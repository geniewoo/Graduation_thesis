import threading
import time

user_input = [None]

# spawn a new thread to wait for input
def get_user_input(user_input_ref):
    user_input_ref[0] = input("Give me some Information: ")

mythread = threading.Thread(target=get_user_input, args=(user_input,))
mythread.daemon = True
mythread.start()

for increment in range(1, 10000):
    time.sleep(0.5)
    print("?")
    if user_input[0] is not None:
        break
