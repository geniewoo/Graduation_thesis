from os import listdir
from os.path import isfile, join
from PIL import Image
import numpy as np
import time
from random import shuffle

IMAGE_SIZE = 224
IMAGE_PATH = "C:\\Users\\psw10\\Downloads\\datasets\\Objective\\"
DARK_DIR = "dark"
FINGER_DIR = "finger"
MEMES_DIR = "memes"
NORMAL_DIR = "normal"
SCREENSHOT_DIR = "screenshot"
SHAKEN_DIR = "shaken"
SMALL_PATH = "smallObjImage\\"
OBJECT_SCORE_FILE = "label_obj.txt"
SMALL_SIZE = (IMAGE_SIZE, IMAGE_SIZE)

score_object_file = open(OBJECT_SCORE_FILE, 'w')
normal_file_names = [f for f in listdir(IMAGE_PATH + NORMAL_DIR) if isfile(join(IMAGE_PATH + NORMAL_DIR, f))]
finger_file_names = [f for f in listdir(IMAGE_PATH + FINGER_DIR) if isfile(join(IMAGE_PATH + FINGER_DIR, f))]
dark_file_names = [f for f in listdir(IMAGE_PATH + DARK_DIR) if isfile(join(IMAGE_PATH + DARK_DIR, f))]
memes_file_names = [f for f in listdir(IMAGE_PATH + MEMES_DIR) if isfile(join(IMAGE_PATH + MEMES_DIR, f))]
screenshot_file_names = [f for f in listdir(IMAGE_PATH + SCREENSHOT_DIR) if isfile(join(IMAGE_PATH + SCREENSHOT_DIR, f))]
shaken_file_names = [f for f in listdir(IMAGE_PATH + SHAKEN_DIR) if isfile(join(IMAGE_PATH + SHAKEN_DIR, f))]
def saveImageWithNewFormat(file_names, dir, label):
    fileList = []
    for i in range(len(file_names)):
        imageFile = Image.open(IMAGE_PATH + dir + "\\" + file_names[i], 'r')

        if not isinstance(imageFile.size[0], int):
            print("not int " + file_names[i])
            continue

        if imageFile.size[0] < IMAGE_SIZE or imageFile.size[1] < IMAGE_SIZE:
            print("too small " + file_names[i])
            continue

        if imageFile.size[0] > imageFile.size[1]:
            box = ((imageFile.size[0] - imageFile.size[1]) / 2, 0, (imageFile.size[0] + imageFile.size[1]) / 2, imageFile.size[1])
            cropImage = imageFile.crop(box)
        else:
            box = (0, (imageFile.size[1] - imageFile.size[0]) / 2, imageFile.size[0], (imageFile.size[1] + imageFile.size[0]) / 2)
            cropImage = imageFile.crop(box)

        smallImage = cropImage.resize(SMALL_SIZE)
        npColorImage = np.array(smallImage)
        if npColorImage.shape != (IMAGE_SIZE, IMAGE_SIZE, 3):
            print("not rgb" + file_names[i])
            continue

        imageName = str(int(round(time.time() * 1000))) + ".png"
        smallImage.save(SMALL_PATH + imageName)

        fileList.append(imageName + " " + label)

    return fileList

normalList = saveImageWithNewFormat(normal_file_names, NORMAL_DIR, "0")
fingerList = saveImageWithNewFormat(finger_file_names, FINGER_DIR, "1")
darkList = saveImageWithNewFormat(dark_file_names, DARK_DIR, "2")
memesList = saveImageWithNewFormat(memes_file_names, MEMES_DIR, "3")
screenshotList = saveImageWithNewFormat(screenshot_file_names, SCREENSHOT_DIR, "4")
shakenList = saveImageWithNewFormat(shaken_file_names, SHAKEN_DIR, "5")

print(NORMAL_DIR + " " + str(len(normalList)))
print(FINGER_DIR + " " + str(len(fingerList)))
print(DARK_DIR + " " + str(len(darkList)))
print(MEMES_DIR + " " + str(len(memesList)))
print(SCREENSHOT_DIR + " " + str(len(screenshotList)))
print(SHAKEN_DIR + " " + str(len(shakenList)))

totalList = normalList
totalList.extend(fingerList)
totalList.extend(darkList)
totalList.extend(memesList)
totalList.extend(screenshotList)
totalList.extend(shakenList)
shuffle(totalList)
for list in totalList:
    score_object_file.write(list + "\n")
    #
    #
    # for i in range(IMAGE_SIZE - 1):
    #     for j in range(IMAGE_SIZE - 1):
    #         # logging.debug(str(i) + " " + str(j))
    #         npColorImage[i][j][0] = npColorImage[i + 1][j + 1][0] - npColorImage[i][j][0]
    #         npColorImage[i][j][1] = npColorImage[i + 1][j + 1][1] - npColorImage[i][j][1]
    #         npColorImage[i][j][2] = npColorImage[i + 1][j + 1][2] - npColorImage[i][j][2]
    #
    # Image.fromarray(npColorImage).save(COLOR_PATH + fileName[0])
