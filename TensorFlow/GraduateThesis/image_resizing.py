from PIL import Image
import numpy as np
# import logging
# logging.basicConfig(filename='./log/test.log', level=logging.DEBUG)

IMAGE_SIZE = 256
DATASET_PATH = "C:\\Users\\psw10\\Downloads\\datasets\\datasetImages\\datasetImages\\"
CROP_PATH = "cropImage\\"
SMALL_PATH = "smallImage\\"
COLOR_PATH = "colorImage\\"
SMALL_SIZE = (IMAGE_SIZE, IMAGE_SIZE)

dataset_array = []
f = open("score.txt", 'r')

while True:
    line = f.readline()
    if line == "":
        break
    dataset_array.append(line.split(" "))

for i in range(len(dataset_array)):
    if i % 1000 == 0:
        print(i)
    fileName = dataset_array[i]
    i += 1
    imageFile = Image.open(DATASET_PATH + fileName[0], 'r')

    if not isinstance(imageFile.size[0], int):
        print("not int " + fileName[0])
        continue

    if imageFile.size[0] < IMAGE_SIZE or imageFile.size[1] < IMAGE_SIZE:
        print("too small " + fileName[0])
        continue

    # print(fileName[0])

    # logging.debug(fileName)
    # logging.debug(i)
    #Crop
    # startIndex = [imageFile.size[0], imageFile.size[1]]
    # startSize = [random.randrange(startIndex[0] - IMAGE_SIZE), random.randrange(startIndex[1] - IMAGE_SIZE)]
    # box = (startSize[0], startSize[1], startSize[0] + IMAGE_SIZE, startSize[1] + IMAGE_SIZE)
    # cropImage = imageFile.crop(box)
    # cropImage.save(CROP_PATH + fileName[0])
    #
    #Size
    # if imageFile.size[0] > imageFile.size[1]:
    #     box = ((imageFile.size[0] - imageFile.size[1]) / 2, 0, (imageFile.size[0] + imageFile.size[1]) / 2, imageFile.size[1])
    #     cropImage = imageFile.crop(box)
    # else:
    #     box = (0, (imageFile.size[1] - imageFile.size[0]) / 2, imageFile.size[0], (imageFile.size[1] + imageFile.size[0]) / 2)
    #     cropImage = imageFile.crop(box)
    #
    # smallImage = cropImage.resize(SMALL_SIZE)
    # smallImage.save(SMALL_PATH + fileName[0])

    #Color
    if imageFile.size[0] > imageFile.size[1]:
        box = ((imageFile.size[0] - imageFile.size[1]) / 2, 0, (imageFile.size[0] + imageFile.size[1]) / 2, imageFile.size[1])
        cropImage = imageFile.crop(box)
    else:
        box = (0, (imageFile.size[1] - imageFile.size[0]) / 2, imageFile.size[0], (imageFile.size[1] + imageFile.size[0]) / 2)
        cropImage = imageFile.crop(box)

    colorImage = cropImage.resize(SMALL_SIZE)
    npColorImage = np.array(colorImage)
    # subNpColorImage = np.empty(shape=[IMAGE_SIZE, IMAGE_SIZE, 3], dtype=int)

    # print(npColorImage.shape)

    if npColorImage.shape != (256, 256, 3):
        print("not rgb" + fileName[0])
        continue


    for i in range(IMAGE_SIZE - 1):
        for j in range(IMAGE_SIZE - 1):
            # logging.debug(str(i) + " " + str(j))
            npColorImage[i][j][0] = npColorImage[i + 1][j + 1][0] - npColorImage[i][j][0]
            npColorImage[i][j][1] = npColorImage[i + 1][j + 1][1] - npColorImage[i][j][1]
            npColorImage[i][j][2] = npColorImage[i + 1][j + 1][2] - npColorImage[i][j][2]

    Image.fromarray(npColorImage).save(COLOR_PATH + fileName[0])