from django.urls import path, include
from VIDFrame import views
from django.contrib.auth import views as auth_views

urlpatterns = [
    path('', views.login_page, name='login'),
    path('register', views.register_page, name='register'),
    path('logout', views.logout_page, name='logout'),
    
    #path('accounts/', include('django.contrib.auth.urls')),
    # or
    path('password_reset/',
         auth_views.PasswordResetView.as_view(template_name='VIDFrame/password reset/password_reset_form.html'),
         name='password_reset'),
    path('password_reset/done/',
         auth_views.PasswordResetDoneView.as_view(template_name='VIDFrame/password reset/password_reset_done.html'),
         name='password_reset_done'),
    path('reset/<uidb64>/<token>/',
         auth_views.PasswordResetConfirmView.as_view(template_name='VIDFrame/password reset/password_reset_confirm.html'),
         name='password_reset_confirm'),
    path('reset/done/',
         auth_views.PasswordResetCompleteView.as_view(template_name='VIDFrame/password reset/password_reset_complete.html'),
         name='password_reset_complete'),
    
    path('home', views.home_page, name='home'),
    path('profile', views.profile_page, name='profile'),
    path('updateProfile', views.update_Profile, name='updateProfile'),
    path('mediaUpload', views.media_upload_page, name='mediaUpload'),
    path('reels', views.reels_page, name='reels'),
    path('<str:userName>/reels', views.reels_page, name='userReels'),
    path('images', views.images_page, name='images'),
    path('<str:userName>/images', views.images_page, name='userImages'),
    path('<str:userName>/imageDetails/<int:mediaId>', views.mediaDetails_page, name='imageDetails'),
    path('<str:userName>/videoDetails/<int:mediaId>', views.mediaDetails_page, name='videoDetails'),
    path('<str:userName>/delReel/<int:mediaId>', views.del_media, name='delReel'),
    path('<str:userName>/delImage/<int:mediaId>', views.del_media, name='delImage'),
    path('addComment/<int:mediaId>', views.add_comment, name='addComment'),
    path('editComment/<int:commentId>/<int:mediaId>', views.edit_comment, name='editComment'),
    path('delComment/<int:commentId>', views.del_comment, name='delComment'),
    path('comments/<int:mediaId>', views.comments_page, name='comments'),
]
