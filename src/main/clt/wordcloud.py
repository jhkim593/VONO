from wordcloud import WordCloud, STOPWORDS
import matplotlib.pyplot as plt
from collections import Counter
import csv
import pandas as pd
from scipy.misc import imread
plt.style.use('ggplot')

#url = "C://Users//LG//Downloads//VONO_word.xlsx"
def wordcloud(url):

	word=pd.read_excel(url)
	text = ''
	for row in word['haha'].values:
    	text = text + row.lower() + ' '
	wc = WordCloud(max_words=2000, stopwords=STOPWORDS,background_color="white",colormap='PuBu')
	wc.generate(text)
	wc.to_file('D://word.png')
	return "OK"
