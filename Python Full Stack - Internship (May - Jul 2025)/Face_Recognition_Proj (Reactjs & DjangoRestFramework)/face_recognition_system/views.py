from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework_simplejwt.tokens import RefreshToken
import face_recognition
import numpy as np
from PIL import Image
import io
import base64

from .serializers import RegisterSerializer, MyTokenObtainPairSerializer
from .models import UserProfile
from rest_framework_simplejwt.views import TokenObtainPairView

# Helper function to get tokens for a user
class MyTokenObtainPairView(TokenObtainPairView):
    serializer_class = MyTokenObtainPairSerializer
    
def get_tokens_for_user(user):
    refresh = RefreshToken.for_user(user)
    return {
        'refresh': str(refresh),
        'access': str(refresh.access_token),
    }

class RegisterView(APIView):
    parser_classes = (MultiPartParser, FormParser)

    def post(self, request, *args, **kwargs):
        serializer = RegisterSerializer(data=request.data)
        if serializer.is_valid():
            user_profile = serializer.save()
            print(user_profile.__str__())
            return Response({"message": "User registered successfully.", "user_id": user_profile.user.id}, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class LoginFaceRecognitionView(APIView):
    parser_classes = (MultiPartParser, FormParser)

    def decodeImgFile(self, user_profile):
        if not user_profile.registered_face_image:
            return None

        image = Image.open(user_profile.registered_face_image).convert('RGB')
        buffer = io.BytesIO()
        image.save(buffer, format='JPEG')
        buffer.seek(0)
        base64_image = base64.b64encode(buffer.read()).decode('utf-8')
        return f"data:image/jpeg;base64,{base64_image}"

    def post(self, request, *args, **kwargs):
        username = request.data.get('username')
        captured_image = request.data.get('captured_image')

        if not username or not captured_image:
            return Response({"error": "Username and captured image are required."}, status=status.HTTP_400_BAD_REQUEST)

        try:
            user_profile = UserProfile.objects.select_related('user').get(user__username=username)
            user = user_profile.user
        except UserProfile.DoesNotExist:
            return Response({"error": "User not found."}, status=status.HTTP_404_NOT_FOUND)

        if not user_profile.face_encoding:
            return Response({"error": "No registered face image encoding found for this user."}, status=status.HTTP_400_BAD_REQUEST)

        known_face_encoding = np.frombuffer(user_profile.face_encoding, dtype=np.float64)

        try:
            captured_image_bytes = captured_image.read()
            captured_face_image = face_recognition.load_image_file(io.BytesIO(captured_image_bytes))
            captured_face_encodings = face_recognition.face_encodings(captured_face_image)

            if not captured_face_encodings:
                return Response({"error": "No face detected in the captured image."}, status=status.HTTP_400_BAD_REQUEST)

            face_encoding_to_check = captured_face_encodings[0]

            matches = face_recognition.compare_faces([known_face_encoding], face_encoding_to_check)
            face_distance = face_recognition.face_distance([known_face_encoding], face_encoding_to_check)

            if matches[0]:
                tokens = get_tokens_for_user(user)
                return Response({
                    "message": "Face recognized. Login successful!",
                    "distance": face_distance[0],
                    "tokens": tokens,
                    "user": {
                               "username": user.username,          
                               "firstname": user.first_name,         
                               "lastname": user.last_name,          
                               "email": user.email,             
                               "contact": user_profile.contact,    
                               "userImage": self.decodeImgFile(user_profile)
                             },
                }, status=status.HTTP_200_OK)
            else:
                return Response({"error": "Face not recognized. Please try again.", "distance": face_distance[0]}, status=status.HTTP_401_UNAUTHORIZED)

        except Exception as e:
            return Response({"error": f"Error processing captured image: {e}"}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)