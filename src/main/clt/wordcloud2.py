
from wordcloud import WordCloud
import matplotlib.pyplot as plt

text= ("Gangnenug GWNU  Abl lab Scipy Lecture Atmosphere Data Datascience nature airpollution time-series spectrum power DB DBcenter")
        
wordcloud = WordCloud(width=500, height=500, margin=0).generate(text)

plt.imshow(wordcloud, interpolation='bilinear')
plt.axis("off")
plt.margins(x=0, y=0)
plt.show()
