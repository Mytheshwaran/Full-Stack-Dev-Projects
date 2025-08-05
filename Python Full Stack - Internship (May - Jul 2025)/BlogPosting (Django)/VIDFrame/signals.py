import os
from django.db.models.signals import post_delete
from django.dispatch import receiver
from .models import Media 
from django.contrib.auth import get_user_model 
from django.conf import settings

User = get_user_model()

@receiver(post_delete, sender=Media)
def auto_delete_file_on_media_delete(sender, instance, **kwargs):
    if instance.file:
        if os.path.isfile(instance.file.path):
            os.remove(instance.file.path)
            print(f"Deleted media file: {instance.file.path}")
        else:
            print(f"Warning: Media file not found for deletion: {instance.file.path}")

@receiver(post_delete, sender=User)
def auto_delete_user_directory_on_user_delete(sender, instance, **kwargs):
    user_media_directory = os.path.join(settings.MEDIA_ROOT, 'uploads', instance.username)

    if os.path.isdir(user_media_directory):
        try:
            import shutil
            shutil.rmtree(user_media_directory)
            print(f"Deleted user media directory: {user_media_directory}")
        except OSError as e:
            print(f"Error: Could not remove user directory {user_media_directory}. {e}")
    else:
        print(f"Warning: User media directory not found for deletion: {user_media_directory}")