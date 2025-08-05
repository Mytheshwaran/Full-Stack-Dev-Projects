# core/admin.py
from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from django.contrib.auth.models import User
from .models import UserProfile

# Define an inline admin descriptor for Profile model
# which acts a bit like a singleton
class ProfileInline(admin.StackedInline):
    model = UserProfile
    can_delete = False
    verbose_name_plural = 'profile'

# New User admin
class UserAdmin(BaseUserAdmin):
    inlines = (ProfileInline,)
    list_display = ('username', 'email', 'first_name', 'last_name', 'is_staff', 'get_contact')

    def get_contact(self, obj):
        return obj.profile.contact if hasattr(obj, 'profile') else ''
    get_contact.short_description = 'Contact'

# Re-register UserAdmin
admin.site.unregister(User)
admin.site.register(User, UserAdmin)