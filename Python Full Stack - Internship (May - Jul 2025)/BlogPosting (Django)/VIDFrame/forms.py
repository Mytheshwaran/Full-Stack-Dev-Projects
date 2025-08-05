from .models import *
from django.contrib.auth.forms import UserCreationForm
from django import forms

class UserRegForm(UserCreationForm):
    
    username = forms.CharField(
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
                'placeholder': 'Enter your username',
            })
    )
    password1 = forms.CharField(widget=forms.PasswordInput(
        attrs={
            'class': 'form-control',
            'placeholder': 'Enter your password',
        })
    )
    password2 = forms.CharField(widget=forms.PasswordInput(
        attrs={
            'class': 'form-control',
            'placeholder': 'Re-enter your password',
        })
    )
    
    first_name = forms.CharField(
        max_length=30,
        required=True,
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
                'placeholder': 'Enter your first name',
            })
    )
    last_name = forms.CharField(
        max_length=30,
        required=True,
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
                'placeholder': 'Enter your last name',
            })
    )
    email = forms.CharField(
        required=True,
        widget=forms.EmailInput(
            attrs={
                'class': 'form-control',
                'placeholder': 'Enter your email',
            })
    )
    profile_pic = forms.ImageField(
        required=False,
        widget=forms.FileInput(
            attrs={
                'class': 'form-control',
            })
    )
    contact = forms.CharField(
        max_length=10, 
        required=False,
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
                'placeholder': 'Enter your Contact number',
            })
    )
    
    class Meta(UserCreationForm.Meta):
        model = User
        Fields = UserCreationForm.Meta.fields + (
            'first_name',
            'last_name',
            'email',
        )
    
    def save(self, commit=True):
        # ... (Your existing save method logic to handle saving User and Profile fields) ...
        # This is where you connect the form fields (profile_pic, contact)
        # to the Profile model instance.
        user = super().save(commit=False)

        user.first_name = self.cleaned_data.get('first_name')
        user.last_name = self.cleaned_data.get('last_name')
        user.email = self.cleaned_data.get('email')

        if commit:
            user.save()

            profile = Profile.objects.create(user=user)
            if self.cleaned_data.get('profile_pic'):
                profile.profile_pic = self.cleaned_data['profile_pic']
            if self.cleaned_data.get('contact'):
                profile.contact = self.cleaned_data['contact']
            profile.save()

        return user
    
class MediaCreationForm(forms.ModelForm): 
    name = forms.CharField(
        required=True,
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
            }
        )
    )
    file = forms.FileField(
        required=True,
        widget=forms.FileInput(
            attrs={
                'class': 'form-control',
            }
        )
    )   
    description = forms.CharField(
        widget=forms.TextInput(
            attrs={
                'class': 'form-control',
            }
        )
    )
    class Meta:
        model = Media
        fields = [
            'name',
            'file',
            'description',
            'type',
        ]