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
    screen = aalib.AsciiScreen(width = 234, height = 120)
    if request.method == 'POST':
        uploaded_file = request.FILES['uploaded_file']
        image = PIL.Image.open(uploaded_file,'r').convert('L').resize(screen.virtual_size)
    
        #image =  PIL.Image.open('lena.jpg','r').convert('L')
    else:
        image =  PIL.Image.open('lena.jpg','r').convert('L').resize(screen.virtual_size)
    #screen = aalib.AsciiScreen()
    
    #image =  PIL.Image.open('lena.jpg','r').convert('L').resize(screen.virtual_size)
    screen.put_image((0,0),image)
    print(screen.virtual_size)
    
    #img = PIL.Image.new('RGB',(int(screen.virtual_size[0]*3),screen.virtual_size[1]*4), color=(0,0,0))
    
    img = PIL.Image.new('RGB',(1410,1800), color=(0,0,0))
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