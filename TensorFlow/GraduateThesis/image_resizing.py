from PIL import Image
import random
IMAGE_SIZE = 256
DATASET_PATH = "C:\\Users\\psw10\\Downloads\\datasets\\datasetImages\\datasetImages\\"
CROP_PATH = "cropImage\\"
SMALL_PATH = "smallImage\\"
SMALL_SIZE = (IMAGE_SIZE, IMAGE_SIZE)

dataset_array = []
f = open("score.txt", 'r')

while True:
    line = f.readline()
    if line == "":
        break
    dataset_array.append(line.split(" "))

i = 0
for fileName in dataset_array:
    if i % 1000 == 0:
        print(i)
    i += 1
    imageFile = Image.open(DATASET_PATH + fileName[0], 'r')

    if not isinstance(imageFile.size[0], int):
        print("not int " + fileName[0])
        continue

    if imageFile.size[0] < IMAGE_SIZE or imageFile.size[1] < IMAGE_SIZE:
        print("too small " + fileName[0])
        continue

    startIndex = [imageFile.size[0], imageFile.size[1]]
    startSize = [random.randrange(startIndex[0] - IMAGE_SIZE), random.randrange(startIndex[1] - IMAGE_SIZE)]
    box = (startSize[0], startSize[1], startSize[0] + IMAGE_SIZE, startSize[1] + IMAGE_SIZE)
    cropImage = imageFile.crop(box)
    cropImage.save(CROP_PATH + fileName[0])

    if imageFile.size[0] > imageFile.size[1]:
        box = ((imageFile.size[0] - imageFile.size[1]) / 2, 0, (imageFile.size[0] + imageFile.size[1]) / 2, imageFile.size[1])
        cropImage = imageFile.crop(box)
    else:
        box = (0, (imageFile.size[1] - imageFile.size[0]) / 2, imageFile.size[0], (imageFile.size[1] + imageFile.size[0]) / 2)
        cropImage = imageFile.crop(box)

    smallImage = cropImage.resize(SMALL_SIZE)
    smallImage.save(SMALL_PATH + fileName[0])