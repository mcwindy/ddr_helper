# pip install Pillow/pillow?
from PIL import Image


def convertImage():
    img = Image.open('image.jpg')
    # img= Image.open("./image.png")
    img = img.convert('RGBA')

    datas = img.getdata()
    newData = []
    for item in datas:
        if item[:3] == (0, 0, 0):
            # 0,0,0: black; 255,255,255: white
            # if item[0] == 255 and item[1] == 255 and item[2] == 255:
            newData.append((0, 0, 0, 0))
        else:
            # newData.append(item)
            # filter as your will
            if sum(item) < 5 * 3 + 255:  # threshold
                newData.append((0, 0, 0, 0))
            else:
                newData.append(item)

    img.putdata(newData)
    img.save('out.png', 'PNG')
    print('Successful')


convertImage()
