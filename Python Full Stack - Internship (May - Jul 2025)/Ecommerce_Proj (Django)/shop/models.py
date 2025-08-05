from django.db import models
from django.contrib.auth.models import User
import datetime
import os

def getFileName(request, fileName):
    now_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    new_fileName = '%s%s' %(now_time, fileName)
    return os.path.join('uploads', new_fileName)

# Create your models here.
class Category(models.Model):
    name = models.CharField(max_length=100, null=False, blank=True)
    image = models.ImageField(upload_to=getFileName, null=True, blank=True)
    description = models.TextField(max_length=200, null=False, blank=False)
    status = models.BooleanField(default=False, help_text='0-show, 1-hidden')
    created_at = models.DateTimeField(auto_now_add=True)
    
    def __str__(self):
        return self.name
    
    class Meta:
        db_table = 'category'
        
class Product(models.Model):
    Category = models.ForeignKey(Category, on_delete=models.CASCADE)
    name = models.CharField(max_length=100, null=False, blank=True)
    vendor = models.CharField(max_length=100, null=False, blank=True)
    product_image = models.ImageField(upload_to=getFileName, null=True, blank=True)
    quantity = models.IntegerField(null=False, blank=False)
    original_price = models.FloatField(null=False, blank=False)
    selling_price = models.FloatField(null=False, blank=False)
    description = models.TextField(max_length=200, null=False, blank=False)
    status = models.BooleanField(default=False, help_text='0-show, 1-hidden')
    trending = models.BooleanField(default=False, help_text='0-show, 1-Trending')
    created_at = models.DateTimeField(auto_now_add=True)
    
    def __str__(self):
        return self.name
    
    class Meta:
        db_table = 'product'
        
class Cart(models.Model):
    User = models.ForeignKey(User, on_delete=models.CASCADE)
    Product = models.ForeignKey(Product, on_delete=models.CASCADE)
    product_qty = models.IntegerField(null=False, blank=False)
    created_at = models.DateTimeField(auto_now_add=True)
    
    @property
    def total_price(self):
        return self.product_qty * self.Product.selling_price
    
    class Meta:
        db_table = 'cart'
        
class Favorite(models.Model):
    User = models.ForeignKey(User, on_delete=models.CASCADE)
    Product = models.ForeignKey(Product, on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    
    class Meta:
        db_table = 'favorite'