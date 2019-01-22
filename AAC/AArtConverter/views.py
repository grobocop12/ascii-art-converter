# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.http import HttpResponse, JsonResponse

from django.shortcuts import render
import aalib
import io
import PIL.Image
import PIL.ImageDraw
import PIL.ImageFont
import os
import uuid
from django.views.decorators.csrf import csrf_exempt, csrf_protect
from django.views.generic import TemplateView
# Create your views here.


@csrf_exempt
def index(request):
    if request.method == 'POST':
        for key in request.FILES:
            print(key)
        uploaded_file = request.FILES['uploaded_file']
        print(type(uploaded_file))
        image = PIL.Image.open(uploaded_file,'r').convert('L')
    
        #image =  PIL.Image.open('lena.jpg','r').convert('L')
    
    size = image.size    
    print(size)
    screen = aalib.AsciiScreen(width = size[0], height=size[1])
    
    #image =  PIL.Image.open('lena.jpg','r').convert('L').resize(screen.virtual_size)
    screen.put_image((0,0),image)
    
    img = PIL.Image.new('RGB',(size[0],size[1]), color=(0,0,0))
    d = PIL.ImageDraw.Draw(img)
    d.text((0,0), screen.render(), fill=(255, 255, 255),align='center' )
    name = str(uuid.uuid4())+'.png'
    img.save('static/'+name, 'png')    
    
    image_data = open("static/"+name, "rb").read()
    data = {
        'url':('/static/'+name)
        }
    
    #return HttpResponse(image_data, content_type="image/png")
    return JsonResponse(data)

