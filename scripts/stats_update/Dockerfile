FROM python:3.6

ENV TZ=Asia/Colombo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

LABEL Name=results-tabulation-tallysheets

# RUN mkdir /app
WORKDIR /app
ADD . /app

# Install requirements
RUN pip install -r requirements.txt
RUN eval $(cat config)

CMD [ "python", "stats_updater.py" ]