# Python Program to compile images from src/commonMain/resources/sprites
# Compile images to src/commonMain/resources/scaledSprites as scaled images by 2x

import os
import sys
# Import the PIL library (pip install pillow)
from PIL import Image

# Scale Factor
scaleFactor = 4

# Origin folder
origin = "commonMain/resources/"

# Get the path of the current directory
path = os.path.dirname(os.path.realpath(__file__))

# Get the path of the sprites directory
spritesPath = os.path.join(path, origin + "sprites")

# Create the scaledSprites directory if it doesn't exist
scaledSpritesPath = os.path.join(path, origin + "scaledSprites")
if not os.path.exists(scaledSpritesPath):
    os.makedirs(scaledSpritesPath)

# Get the list of files in the sprites directory
files = os.listdir(spritesPath)

# Recursive function because we need to go through all the subdirectories
def compileImages(path, folder = ""):
    # Get the list of files in the directory
    files = os.listdir(path)
    for file in files:
        # Get the full path of the file
        filePath = os.path.join(path, file)
        # Check if the file is a directory
        if os.path.isdir(filePath):
            # If it is a directory, call the function again
            compileImages(filePath, folder + file + "/")
        else:
            # If it is a file, check if it is a png file
            if file.endswith(".png"):
                # If it is a png file, open it
                image = Image.open(filePath)
                # Scale the image by Scale Factor
                image = image.resize((image.width * scaleFactor, image.height * scaleFactor), Image.BOX)
                # Check if the folder exists
                if not os.path.exists(os.path.join(scaledSpritesPath, folder)):
                    # If it doesn't exist, create it
                    os.makedirs(os.path.join(scaledSpritesPath, folder))
                # Get the path of the scaled image
                scaledImagePath = os.path.join(scaledSpritesPath, folder + file)
                # Save the scaled image
                image.save(scaledImagePath)

# Call the function
compileImages(spritesPath)

# Print a message to the console
print("Compiled images successfully!")
