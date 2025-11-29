## OOD - Music Organizer Project

This is our final solution for the Object Oriented Design course, which was **graded 5/5**. Music Organizer is a **desktop application** for managing and playing audio files in a **hierachical album structure**. It has an undo/redo feature, **Search based albums** (flagged soundclips, great sound clips)

---

### Notes
- Uses **Design-by-Contract** with java assertions (post/pre conditions)
- **Memento Pattern** for undo/redo
- Follows **Model-View-Controller** pattern
- Coded as a **pair programming** project
- Albums have a **tree datastructure**
- Iterative methods to removeClipFromSubAlbums and addClipToSubAlbums

---

### GUI
<img width="754" height="521" alt="image" src="https://github.com/user-attachments/assets/98c93bad-9f2f-4bc1-af58-d2af1ef1620a" />

### Albums structure
<img width="239" height="394" alt="image" src="https://github.com/user-attachments/assets/0fe14952-6968-4a3e-9993-fbde868dface" />

- A clip added to a subalbum is automatically added to the parent album
- A clip removed from the parent album is removed from the sub albums

---
### How to Run
Now this project uses **Maven** and **JavaFX 21** (dependencies in pom file)

#### Requirements
- Java JDK 17
- Maven installed
Right now the pom file is configured for mac-aarch64:
```xml
<javafx.platform>mac-aarch64</javafx.platform>
```
Change: 
```xml
<javafx.platform>win</javafx.platform>   <!-- for Windows -->
<javafx.platform>linux</javafx.platform> <!-- for Linux -->
```

#### Steps
1. Clone
```bash
git clone https://github.com/AlberthMartin/OOD-Course-MusicOrganizer-App.git
cd OOD-Course-MusicOrganizer-App
```
2. Build
```bash
mvn clean install
```
3. Run
```bash
mvn javafx:run
``` 
