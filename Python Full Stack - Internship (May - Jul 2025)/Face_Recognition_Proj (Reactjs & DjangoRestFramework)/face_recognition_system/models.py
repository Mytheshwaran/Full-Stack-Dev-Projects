from django.db import models
from django.contrib.auth.models import User 

class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, related_name='profile') 
    contact = models.CharField(max_length=10, blank=True, null=True)
    registered_face_image = models.ImageField(upload_to='medias/', blank=True, null=True)
    face_encoding = models.BinaryField(blank=True, null=True) 

    def __str__(self):
        return self.user.username
    
    class Meta:
        db_table = 'Profile'