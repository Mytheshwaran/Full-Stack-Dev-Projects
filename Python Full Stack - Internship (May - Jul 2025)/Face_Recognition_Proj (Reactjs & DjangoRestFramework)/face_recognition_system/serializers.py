from rest_framework import serializers
from .models import UserProfile
from django.contrib.auth.models import User
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
import face_recognition
import numpy as np
from PIL import Image, UnidentifiedImageError
import io

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'first_name', 'last_name')

class UserProfileSerializer(serializers.ModelSerializer):
    user = UserSerializer(read_only=True)

    class Meta:
        model = UserProfile
        fields = ('id', 'user', 'contact', 'registered_face_image')
        read_only_fields = ('face_encoding',) 

class RegisterSerializer(serializers.ModelSerializer):
    username = serializers.CharField(write_only=True)
    first_name = serializers.CharField(write_only=True, required=False)
    last_name = serializers.CharField(write_only=True, required=False)
    contact = serializers.CharField(write_only=True, required=False)
    email = serializers.EmailField(write_only=True)
    registered_face_image = serializers.ImageField(write_only=True, required=False) 

    class Meta:
        model = UserProfile
        fields = ('username', 'first_name', 'last_name', 'email', 'contact', 'registered_face_image')

    def create(self, validated_data):
        username = validated_data.pop('username')
        firstname = validated_data.pop('first_name', '')
        lastname = validated_data.pop('last_name', '')
        contact = validated_data.pop('contact', '')
        email = validated_data.pop('email')
        registered_face_image = validated_data.pop('registered_face_image', None)

        # Create user and user profile
        user = User.objects.create(username=username, first_name=firstname, last_name=lastname, email=email)
        profile = UserProfile.objects.create(user=user)

        if contact:
            profile.contact = contact

        if registered_face_image:
            try:
                image_bytes = registered_face_image.read()
                profile.registered_face_image=registered_face_image
                
                reg_face_image = face_recognition.load_image_file(io.BytesIO(image_bytes))
                face_encodings = face_recognition.face_encodings(reg_face_image)


                if face_encodings:
                    profile.face_encoding = face_encodings[0]
                    profile.registered_face_image = registered_face_image  # Save the file
                else:
                    raise serializers.ValidationError({
                        "registered_face_image": "No face detected. Please upload a clear image with your face visible."
                    })

            except UnidentifiedImageError as e:
                raise serializers.ValidationError({
                    "registered_face_image": f"Invalid image file format: {e}"
                })

            except Exception as e:
                raise serializers.ValidationError({
                    "registered_face_image": f"Unexpected error during face recognition: {e}"
                })

        user.save()
        profile.save()
        return profile

class MyTokenObtainPairSerializer(TokenObtainPairSerializer):
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)

        # Add custom claims
        token['username'] = user.username
        token['email'] = user.email
        # can add other user-related data here, e.g., if you had a profile
        # try:
        #     token['profile_id'] = user.profile.id
        # except UserProfile.DoesNotExist:
        #     pass

        return token