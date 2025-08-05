from django.shortcuts import render, redirect
from .models import *
from django.contrib import messages
from .forms import CustomUserForm
from django.contrib.auth import authenticate, login, logout
from django.http import JsonResponse
import json

# Create your views here.
def home(request):
    products = Product.objects.filter(trending=1)
    return render(request, 'shop/index.html', {"products": products})

def register(request):
    form = CustomUserForm()
    if request.method == 'POST':
        form = CustomUserForm(request.POST)
        if form.is_valid():
            form.save()
            messages.success(request, 'Registration Success, Now you can login')
            return redirect('/login')
    return render(request, 'shop/register.html', {'form': form})

def login_page(request):
    if request.user.is_authenticated:
        return redirect('/')
    else:
        if request.method == 'POST':
            name = request.POST.get('username')
            pwd = request.POST.get('password')
            user = authenticate(request, username=name, password=pwd)
            if user is not None:
                login(request, user)
                messages.success(request, 'Logged-in Successfully')
                return redirect('/')
            else:
                messages.error(request, 'Invalid credentials')
                return redirect('/')
    return render(request, 'shop/login.html')

def logout_page(request):
    if request.user.is_authenticated:
        logout(request)
        messages.success(request, 'Logged-out Successfully')
    return redirect('/')
        

def collections(request):
    categories = Category.objects.filter(status=0)
    return render(request, 'shop/collections.html', {'categories': categories})

def collectionsView(request, name):
    if(Category.objects.filter(name=name, status=0)):
        products = Product.objects.filter(Category__name=name)
        return render(request, 'shop/products/index.html', {'products': products, "category_name": name})
    else:
        messages.warning(request, 'No such category found.')
        return redirect('collections')
    
def product_details(request, cname, pname):
    if(Category.objects.filter(name=cname, status=0)):
        if(Product.objects.filter(name=pname, status=0)):
             product = Product.objects.filter(name=pname, status=0).first()
             return render(request, 'shop/products/product_details.html', {'product': product})
        else:
            messages.warning(request, 'No such Product found.')
            return redirect('collections')
    else:
        messages.warning(request, 'No such category found.')
        return redirect('collections')
    
def add_to_cart(request):
    if request.headers.get('X-Requested-With') == 'XMLHttpRequest':
        if request.user.is_authenticated:
            data = json.loads(request.body.decode('utf-8'))
            product_qty = data['product_qty']
            product_id = data['pid']
            product_status = Product.objects.get(id=product_id)
            if product_status:
                if Cart.objects.filter(User=request.user, Product=Product.objects.get(id=product_id)):
                    return JsonResponse({'status':'Product Already added in Cart'}, status=200)
                else:
                    if product_status.quantity >= product_qty:
                        Cart.objects.create(User=request.user, Product=Product.objects.get(id=product_id), product_qty=product_qty)
                        return JsonResponse({'status':'Product Added to Cart'}, status=200)
                    else:
                        return JsonResponse({'status':'Product Stock Not Available'}, status=200)
            else:
                return JsonResponse({'status': 'Invalid Product Id'}, status=200)
        else:
            return JsonResponse({'status':'Login to Add Cart'}, status=200)
    else:
        return JsonResponse({'status':'Invalid Access'}, status=200)                
        
def cart_page(request):
    if request.user.is_authenticated:
        carts = Cart.objects.filter(User=request.user)
        return render(request, 'shop/cart.html', {'carts': carts})
    messages.info(request, 'Login to view Cart Page')
    return redirect('/')

def remove_cart(request, cid):
    cartItem = Cart.objects.get(id=cid)
    cartItem.delete()
    return redirect('/cart')
        
def add_to_fav(request):
    if request.user.is_authenticated:
        data = json.loads(request.body.decode('utf-8'))
        product_id = data['pid']
        product_status = Product.objects.get(id=product_id)
        if product_status:
            if Favorite.objects.filter(User=request.user.id, Product=Product.objects.get(id=product_id)):
                return JsonResponse({'status':'Product Already added in Favorite'}, status=200)
            else:
                Favorite.objects.create(User=request.user, Product=Product.objects.get(id=product_id))
                return JsonResponse({'status':'Product Added to Favorite'}, status=200)
        else:
            return JsonResponse({'status': 'Invalid Access'}, status=200)
    else:
        return JsonResponse({'status':'Login to Add Favorite'}, status=200)
    
def fav_page(request):
    if request.user.is_authenticated:
        favorites = Favorite.objects.filter(User=request.user)
        return render(request, 'shop/favorite.html', {'favorites': favorites})
    messages.info(request, 'Login to view Favorite Page')
    return redirect('/')

def remove_fav(request, fid):
    favItem = Favorite.objects.get(id=fid)
    favItem.delete()
    return redirect('/fav')