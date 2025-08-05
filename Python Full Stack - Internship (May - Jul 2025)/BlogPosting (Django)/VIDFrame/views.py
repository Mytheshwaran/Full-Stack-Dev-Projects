from django.shortcuts import render, redirect
from .models import *
from .forms import UserRegForm, MediaCreationForm
from django.contrib import messages
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.http import JsonResponse
from django.db import transaction

# Create your views here.
def register_page(request):
    form = UserRegForm()
    if request.method == 'POST':
        form = UserRegForm(request.POST, request.FILES)
        if form.is_valid():
            form.save()
            messages.success(request, 'Registration Success, Now you can login.')
            return redirect('/')
    return render(request, 'VIDFrame/register.html',{'form': form})

def login_page(request):
    if request.user.is_authenticated:
        return redirect('home')
    else:
        if request.method == 'POST':
            username = request.POST.get('username')
            password = request.POST.get('password')
            user = authenticate(request, username=username, password=password)
            if user is not None:
                login(request, user)
                messages.success(request,'User Logged-in Successfully')
                return redirect('home')
            else:
                messages.info(request,'User Credentials Invalid')
                return redirect('/')
    return render(request, 'VIDFrame/login.html')

def logout_page(request):
    if request.user.is_authenticated:
        logout(request)
        messages.success(request,'Logged-out Successfully')
    return redirect('/')

@login_required
def home_page(request):
    return render(request, 'VIDFrame/home.html')

@login_required
def profile_page(request):
    user = request.user
    profile = Profile.objects.filter(user=user.id).first()
    return render(request, 'VIDFrame/user/profile.html', {'profile': profile})

@login_required
def update_Profile(request):
    if request.headers.get('X-Requested-With') == 'XMLHttpRequest' and request.method == 'POST':
        user = request.user
        profile = Profile.objects.filter(user=user.id).first()
        with transaction.atomic():
            try:
                data = request.POST
                file = request.FILES
                updatedFields = {}
                if 'username' in data and data['username'] != user.username:
                    user.username = data['username']
                    updatedFields['username'] = True
                    
                if 'firstname' in data and data['firstname'] != user.first_name:
                    user.first_name = data['firstname']
                    updatedFields['firstname'] = True
                    
                if 'lastname' in data and data['lastname'] != user.last_name:
                    user.last_name = data['lastname']
                    updatedFields['lastname'] = True
                    
                if 'email' in data and data['email'] != user.email:
                    if '@' in request.POST['email']:
                        user.email = data['email']
                        updatedFields['email'] = True
                    else:
                        return JsonResponse({'status': 'error', 'message': 'Invalid email format'}, status=400)
                    
                if 'contact' in data and data['contact'] != profile.contact:
                    profile.contact = data['contact']
                    updatedFields['contact'] = True
                    
                if 'profile_pic' in file:
                    profile.profile_pic = file['profile_pic']
                    updatedFields['profile_pic'] = True

                if 'old_pwd' in data or 'new_pwd1' in data or 'new_pwd2' in data:
                    old_pwd = data['old_pwd']
                    new_pwd1 = data['new_pwd1']
                    new_pwd2 = data['new_pwd2']
                    if not user.check_password(old_pwd):
                        return JsonResponse({'status': 'error', 'message': 'Old password does not match.'}, status=400)
                    if new_pwd1 != new_pwd2:
                        return JsonResponse({'status': 'error', 'message': 'New passwords do not match.'}, status=400)

                    user.set_password(new_pwd1)
                    updatedFields['new_pwd'] = True

                if updatedFields.keys():
                    user.save()
                    profile.save()
                    return JsonResponse({'status': 'success', 'message': 'Profile updated successfully!'})
            except Exception as e:
                transaction.set_rollback(True)
                return JsonResponse({'status': 'error', 'message': f'Database error: {str(e)}'}, status=500)
            else:
                return JsonResponse({'status': 'success', 'message': 'No Fields to change!'})
        return JsonResponse({'status': 'success', 'message': 'Invalid Access!'})
    
@login_required
def media_upload_page(request):
    is_ajax = request.headers.get('x-requested-with') == 'XMLHttpRequest'

    if request.method == 'POST':
        form = MediaCreationForm(request.POST, request.FILES)
        if form.is_valid():
            media_instance = form.save(commit=False)
            media_instance.user = request.user 
            media_instance.save()
            
            if is_ajax:
                return JsonResponse({'status': 'success', 'message': 'Media uploaded successfully!'})
            else:
                messages.success(request, 'Media Saved Successfully')
                return redirect('/mediaUpload')
        else:
            if is_ajax:
                return JsonResponse({'status': 'error', 'errors': form.errors}, status=400) 
            else:
                messages.error(request, 'Error saving media. Please correct the errors.')
    else: # GET request
        form = MediaCreationForm()
    
    if is_ajax:
        return render(request, 'VIDFrame/user/mediaUpload.html', {'form': form})
    else:
        return render(request, 'VIDFrame/user/mediaUpload.html', {'form': form})

@login_required
def mediaDetails_page(request, mediaId, userName=None):
    userObj = request.user if (request.user.username == userName and userName!=None) else userName
    userName = '' if (userObj == None) else userObj.username
    media = Media.objects.filter(id=mediaId,user=userObj.id).first()
    if request.path == '/'+ userName +'/imageDetails/'+str(mediaId):
        if request.method == 'GET':
            messages.success(request, 'Image Details load Successfully')
    elif request.path == '/'+ userName +'/videoDetails/'+str(mediaId):
        if request.method == 'GET':
            messages.success(request, 'Reel Details load Successfully')
        
    if request.method == 'POST':
        data = request.POST
        fieldEdited = {}
        if 'name' in data:
            media.name = data['name']
            fieldEdited['name'] = True
            
        if 'description' in data:
            media.description = data['description']
            fieldEdited['description'] = True
            
        if fieldEdited:
            media.save()
            if request.path == '/'+ userName +'/videoDetails/'+str(mediaId):
                messages.success(request, 'Video Fields Edited Successfully')
            elif request.path == '/'+ userName +'/imageDetails/'+str(mediaId):
                messages.success(request, 'Image Fields Edited Successfully')
                
    return render(request, 'VIDFrame/user/mediaDetails.html', {'media':media})
        
@login_required
def del_media(request, mediaId, userName=None):
    userObj = request.user if (request.user.username == userName and userName!=None) else userName
    userName = '' if (userObj == None) else userObj.username
    
    media = Media.objects.filter(id=mediaId, user=userObj.id)
    media.delete()
    if request.path == '/'+userName+'/delReel/'+str(mediaId):
        messages.success(request,'Reel Deleted Successfully')
        return redirect('/'+userName+'/reels')
    elif request.path == '/'+userName+'/delImage/'+str(mediaId):
        messages.success(request,'Image Deleted Successfully')
        return redirect('/'+userName+'/images')
    
@login_required
def reels_page(request, userName=None):
    if request.method == 'GET':
        userObj = request.user if (request.user.username == userName and userName!=None) else userName
        userName = '' if (userObj == None) else userObj.username
        if request.path == '/'+ userName +'/reels':
            medias = Media.objects.filter(user=userObj.id,type='video')
            profile = Profile.objects.filter(user=userObj.id).first()
            return render(request, 'VIDFrame/user/reels.html', {'videos':medias,'profile':profile})
        else:
            medias = Media.objects.filter(user__is_active=True, type='video')
    return render(request,'VIDFrame/reels.html', {'videos':medias})

@login_required
def images_page(request, userName=None):
    if request.method == 'GET':
        userObj = request.user if (request.user.username == userName and userName!=None) else userName
        userName = '' if (userObj == None) else userObj.username
        if request.path == '/'+ userName +'/images':
            medias = Media.objects.filter(user=userObj.id,type='image')
            profile = Profile.objects.filter(user=userObj.id).first()
            return render(request, 'VIDFrame/user/images.html', {'images':medias,'profile':profile})
        else:
            medias = Media.objects.filter(user__is_active=True, type='image')
    return render(request,'VIDFrame/images.html', {'images':medias})

@login_required
def comments_page(request, mediaId):
    comments = MediaComment.objects.filter(media=mediaId)
    media = Media.objects.get(id=mediaId)
    messages.success(request, 'Media Comments loaded successfully')
    return render(request, 'VIDFrame/user/comments.html', {'comments': comments, 'media': media})

@login_required
def add_comment(request, mediaId):
    if request.method == 'POST':
        userObj = request.user
        mediaObj = Media.objects.get(id=mediaId)
        commentObj = MediaComment(comment_text = request.POST['comment_text'],posted_by_user = userObj,media = mediaObj)
        commentObj.save()
        messages.success(request, 'Comment Added Successfully')
        if mediaObj.type == 'video':
            if userObj.id == mediaObj.user.id:
                return redirect('/'+request.user.username+'/videoDetails/'+str(mediaObj.id))
            else:
                return redirect('/reels')
        elif mediaObj.type == 'image':
            if userObj.id == mediaObj.user.id:
                return redirect('/'+request.user.username+'/imageDetails/'+str(mediaObj.id))  
            else:
                return redirect('/images')      
        else:
            return redirect('/home')

@login_required
def edit_comment(request, commentId, mediaId):
    comment = MediaComment.objects.get(id=commentId, media=mediaId)
    if comment != None:
        media = comment.media
        comment.comment_text = request.POST['comment_text']
        comment.save()
        if media.type == 'video':
            messages.success(request,'Reel Comment Edited Successfully')
            return redirect('/comments/'+str(media.id))
        elif media.type == 'image':
            messages.success(request,'Image Comment Edited Successfully')
            return redirect('/comments/'+str(media.id))
    else:
        messages.info(request, 'User have no such media')
        return redirect('comments/'+str(commentId))

@login_required
def del_comment(request, commentId):
    comment = MediaComment.objects.filter(posted_by_user=request.user, id=commentId).first()
    media = comment.media
    comment.delete()
    if media.type == 'video':
        messages.success(request,'Reel Comment Deleted Successfully')
        return redirect('/comments/'+str(media.id))
    elif media.type == 'image':
        messages.success(request,'Image Comment Deleted Successfully')
        return redirect('/comments/'+str(media.id))
    else:
        messages.info(request, "You don't have that video")
        return redirect("/home")