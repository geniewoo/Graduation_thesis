def make_arrays(nb_rows, img_size): # 빈 리스트 생성
  if nb_rows:
    dataset = np.ndarray((nb_rows, img_size, img_size), dtype=np.float32)
    labels = np.ndarray(nb_rows, dtype=np.int32)
  else:
    dataset, labels = None, None
  return dataset, labels

def merge_datasets(pickle_files, train_size, valid_size=0): # pickle 데이터를 모두 모아서 하나로 만듬
  num_classes = len(pickle_files)
  valid_dataset, valid_labels = make_arrays(valid_size, image_size) # image_size 는 28, 빈 리스트 생성
  train_dataset, train_labels = make_arrays(train_size, image_size)
  vsize_per_class = valid_size // num_classes #A~J까지 클래스마다 몇개씩 가져와야 하는지
  tsize_per_class = train_size // num_classes

  start_v, start_t = 0, 0
  end_v, end_t = vsize_per_class, tsize_per_class
  end_l = vsize_per_class+tsize_per_class
  for label, pickle_file in enumerate(pickle_files): # label 에는 숫자, pickle_file에는 pickle파일이름 하나가 들어간다.
    try:
      with open(pickle_file, 'rb') as f:
        letter_set = pickle.load(f) # 파일내용 전부다 로드하기
        # let's shuffle the letters to have random validation and training set
        np.random.shuffle(letter_set)
        if valid_dataset is not None:
          valid_letter = letter_set[:vsize_per_class, :, :] # validation dataset에 앞에서 validation size만큼 넣어준다
          valid_dataset[start_v:end_v, :, :] = valid_letter
          valid_labels[start_v:end_v] = label # label도 넣어준다.
          start_v += vsize_per_class # 다음 인덱스로 옮긴다.
          end_v += vsize_per_class # 다음 인덱스로 옮긴다.

        train_letter = letter_set[vsize_per_class:end_l, :, :]
        train_dataset[start_t:end_t, :, :] = train_letter
        train_labels[start_t:end_t] = label
        start_t += tsize_per_class
        end_t += tsize_per_class
    except Exception as e:
      print('Unable to process data from', pickle_file, ':', e)
      raise

  return valid_dataset, valid_labels, train_dataset, train_labels


train_size = 200000
valid_size = 10000
test_size = 10000

valid_dataset, valid_labels, train_dataset, train_labels = merge_datasets(train_datasets, train_size, valid_size) # train_datasets는 피클파일이름들
_, _, test_dataset, test_labels = merge_datasets(test_datasets, test_size)

print('Training:', train_dataset.shape, train_labels.shape)
print('Validation:', valid_dataset.shape, valid_labels.shape)
print('Testing:', test_dataset.shape, test_labels.shape)
