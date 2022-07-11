# Capstone project
## Processing video stream

### Idea
To build pipeline that will process video stream. Tries to identify objects on video (what they are and how they're moving)

### High-level design

Prepare a data set - a video file that consists of some objects.\
Prepare a Pub/Sub (kafka) environment.\
Generator microservice that splits the video file to frames, sends them to Pub/Sub as messages.\
Microservice that preprocesses video frames.\
Microservice that detects objects in video frames.\
Microservice that tracks an objects in video frames.\
Microservice that generates and displays statistics :
* number of detected unique objects
* ...

### Diagrams
draft of architecture, not a finale version. But I hope that concept is understandable and will stay as it is
![high level design of architecture](../../files/homeworks/2_capstone_and_faas/architecture_design.png)

