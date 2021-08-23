
import matplotlib.pyplot as plt
import csv
import pandas as pd
word=pd.read_excel("C://Users//LG//Downloads//VONO_word.xlsx")
a=word['haha'].values
from collections import Counter
c=Counter(a)
d=dict(c)
json_val = json.dumps(d)