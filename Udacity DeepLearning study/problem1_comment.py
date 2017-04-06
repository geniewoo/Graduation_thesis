image_size = 28  # Pixel width and height.
pixel_depth = 255.0  # Number of levels per pixel.

def load_letter(folder, min_num_images): #forder 은 ./large/A, B, C, --- 의 형태
  """Load the data for a single letter label."""
  image_files = os.listdir(folder)  #폴더안의 파일 이름을 리스트로 가져옴
  dataset = np.ndarray(shape=(len(image_files), image_size, image_size), dtype=np.float32) # 파일 개수만큼의 빈 array 생성
  print(folder)  #array
  num_images = 0
  for image in image_files:
    image_file = os.path.join(folder, image) #image_file은 낱개 파일 이름이다.
    try:
      image_data = (ndimage.imread(image_file).astype(float) - pixel_depth / 2) / pixel_depth # ndimage.imread(파일이름)은 이미지를 배열 형태로 읽어온다. 예제에서는 흑백이므로 [28] * [28]의 형태로 가져온다. astype(float)은 float형태로 가져온다. 그 후 -1 ~ 1 사이의 값으로 배열전체 크기를 조정한다.
      if image_data.shape != (image_size, image_size): # 이미지사이즈가 원하는 사이즈가 맞는지 확인(예제에서는 28*28)
        raise Exception('Unexpected image shape: %s' % str(image_data.shape))
      dataset[num_images, :, :] = image_data # dataset에 집어넣는다
      num_images = num_images + 1
    except IOError as e:
      print('Could not read:', image_file, ':', e, '- it\'s ok, skipping.')

  dataset = dataset[0:num_images, :, :] # 모든 파일을 이상없이 읽을 것이라 예상하고 했으므로 성공한 파일 이미지사이즈로 줄여준다.
  if num_images < min_num_images:
    raise Exception('Many fewer images than expected: %d < %d' % (num_images, min_num_images))

  print('Full dataset tensor:', dataset.shape)
  print('Mean:', np.mean(dataset)) # 평균
  print('Standard deviation:', np.std(dataset)) # 표준편차
  return dataset

def maybe_pickle(data_folders, min_num_images_per_class, force=False): # data_folders는 [./large/A, ./large/B, ---]의 list형태
  dataset_names = []
  for folder in data_folders:
    set_filename = folder + '.pickle'
    dataset_names.append(set_filename)
    if os.path.exists(set_filename) and not force: #이미 정보를 추출한 파일이 있으면 중복하지않는다.
      # You may override by setting force=True.
      print('%s already present - Skipping pickling.' % set_filename)
    else:
      print('Pickling %s.' % set_filename)
      dataset = load_letter(folder, min_num_images_per_class) # 이미지파일에서 정보를 가져온다.
      try:
        with open(set_filename, 'wb') as f:
          pickle.dump(dataset, f, pickle.HIGHEST_PROTOCOL)
      except Exception as e:
        print('Unable to save data to', set_filename, ':', e)

  return dataset_names

train_datasets = maybe_pickle(train_folders, 45000)
test_datasets = maybe_pickle(test_folders, 1800)
