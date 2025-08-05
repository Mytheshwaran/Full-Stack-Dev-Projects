from django.apps import AppConfig


class VidframeConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'VIDFrame'

    def ready(self):
        # Import your signals here to ensure they are registered when the app loads
        import VIDFrame.signals