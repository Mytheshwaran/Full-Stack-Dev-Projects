from django.db import models
from django.contrib.auth import get_user_model
import datetime
import os

# Create your models here.
User = get_user_model()

def uploadToPath(instance, fileName, dir_Name):
    username = instance.user.username if instance.user else None
    now_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    new_fileName = '%s%s%s' %(now_time, username, fileName)
    return os.path.join(f'uploads/{username}/{dir_Name}', new_fileName)

def upload_To_Profile_Pic(instance, fileName):
    return uploadToPath(instance, fileName, 'profile_pic')

def upload_To_Medias(instance, fileName):
    return uploadToPath(instance, fileName, 'medias')

class Profile(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    profile_pic = models.ImageField(upload_to=upload_To_Profile_Pic, null=True, blank=True)
    contact = models.CharField(null=True, blank=True, max_length=10, unique=True)
    
    class Meta:
        db_table = 'profile'
        
class Media(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    name = models.CharField(max_length=100, null=False, blank=False)
    file = models.FileField(upload_to=upload_To_Medias, null=True, blank=True)
    MEDIA_TYPES = (
        ('', '--- Choose Media Type ---'),
        ('image', 'Image'),
        ('video', 'Video'),
        # Add other types if needed later, e.g., 'audio', 'document'
    )
    type = models.CharField(max_length=50, choices=MEDIA_TYPES, default='')
    description = models.TextField(max_length=200,null=True,blank=True)
    created_datetime = models.DateTimeField(
        auto_now_add=True
    )

    def __str__(self):
        return "%s" %(self.name)

    class Meta:
        db_table = 'media' 
        ordering = ['created_datetime'] 
        
    # Override save method to set the 'type' field automatically
    def save(self, *args, **kwargs):
        if self.file and not self.type: # Only set if file exists and type is not already set
            content_type = self.file.file.content_type if hasattr(self.file, 'file') else self.file.content_type

            if content_type.startswith('image/'):
                self.type = 'image'
            elif content_type.startswith('video/'):
                self.type = 'video'
            else:
                self.type = 'unknown' 
                print(f"Warning: Unknown file type detected: {content_type} for file {self.file.name}")
        
        if not self.name and self.file:
            self.name = os.path.splitext(os.path.basename(self.file.name))[0] # Set name from filename if not provided

        super().save(*args, **kwargs)

class MediaComment(models.Model):
    media = models.ForeignKey(Media, on_delete=models.CASCADE)
    comment_text = models.TextField(max_length=250,null=False, blank=False)
    created_datetime = models.DateTimeField(auto_now_add=True)
    posted_by_user = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return "%s" %(self.comment_text)
    
    class Meta:
        db_table = 'media_comment'
        ordering = ['created_datetime'] 
