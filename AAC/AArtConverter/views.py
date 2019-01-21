# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.http import HttpResponse
from django.shortcuts import render
import aalib
import io
import PIL.Image
import PIL.ImageDraw
import PIL.ImageFont


# Create your views here.


def index(request):
    
    '''
    if (request.method =="POST"):
        uploaded_file = request.files['file']
        
        return response
    
    return HttpResponse()
    '''
    image =  PIL.Image.open('lena.jpg','r').convert('L')
    
    size = image.size    
    print(size)
    screen = aalib.AsciiScreen(width = size[0], height=size[1])
    
    image =  PIL.Image.open('lena.jpg','r').convert('L').resize(screen.virtual_size)
    screen.put_image((0,0),image)
    
    img = PIL.Image.new('RGB',(screen.virtual_size[0]*3,screen.virtual_size[1]*7), color=(0,0,0))
    d = PIL.ImageDraw.Draw(img)
    d.text((0,0), screen.render(), fill=(255, 255, 255),align='center' )
    img.save('image.jpg', 'png')    
    
    image_data = open("image.jpg", "rb").read()
    return HttpResponse(image_data, content_type="image/jpg")

