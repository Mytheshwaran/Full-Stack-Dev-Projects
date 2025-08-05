from django.urls import path
from .views import RegisterView, LoginFaceRecognitionView, MyTokenObtainPairView
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path('register/', RegisterView.as_view(), name='register'),
    path('login/', LoginFaceRecognitionView.as_view(), name='login'),
    path('token/', MyTokenObtainPairView.as_view(), name='token_obtain_pair'),
]+ static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
