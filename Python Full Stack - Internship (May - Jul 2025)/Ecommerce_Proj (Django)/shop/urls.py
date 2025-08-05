from django.urls import path
from shop import views

urlpatterns = [
    path('', views.home, name='home'),
    path('register', views.register, name='register'),
    path('login', views.login_page, name='login'),
    path('logout', views.logout_page, name='logout'),
    path('collections', views.collections, name='collections'),
    path('collections/<str:name>', views.collectionsView, name='collections'),
    path('collections/<str:cname>/<str:pname>', views.product_details, name='product_details'),
    path('addToCart', views.add_to_cart, name='addToCart'),
    path('cart', views.cart_page, name='cart'),
    path('remove_cart/<str:cid>', views.remove_cart, name='remove_cart'),
    path('addToFav', views.add_to_fav, name='addToFav'),
    path('fav', views.fav_page, name='fav'),
    path('remove_fav/<str:fid>', views.remove_fav, name='remove_fav'),
]
