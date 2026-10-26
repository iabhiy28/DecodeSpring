interface Playable {
    void play();
}

interface Downloadable {
    void download();
}

// Song implements BOTH interfaces — can be played AND downloaded
class Song implements Playable, Downloadable {
    private String title;

    public Song(String title) {
        this.title = title;
    }

    @Override
    public void play() {
        System.out.println("Playing song: " + title);
    }

    @Override
    public void download() {
        System.out.println("Downloading song: " + title);
    }
}

// Podcast — only Playable, cannot be downloaded
class Podcast implements Playable {
    private String title;

    public Podcast(String title) {
        this.title = title;
    }

    @Override
    public void play() {
        System.out.println("Playing podcast: " + title);
    }
}

// LiveStream — only Playable, cannot be downloaded
class LiveStream implements Playable {
    private String title;

    public LiveStream(String title) {
        this.title = title;
    }

    @Override
    public void play() {
        System.out.println("Playing live stream: " + title);
    }
}

// MediaPlayer composes with Playable — works with any Playable content
class MediaPlayer {
    private Playable content; // HAS-A Playable (composition)

    public MediaPlayer(Playable content) {
        this.content = content;
    }

    public void play() {
        content.play(); // delegates — doesn't care what type it is
    }
}

public class Exercise1 {
    public static void main(String[] args) {
        Song song = new Song("Blinding Lights");
        Podcast podcast = new Podcast("Java Deep Dive");
        LiveStream stream = new LiveStream("Live Concert");

        MediaPlayer player = new MediaPlayer(song);
        player.play(); // Playing song: Blinding Lights

        player = new MediaPlayer(podcast);
        player.play(); // Playing podcast: Java Deep Dive

        player = new MediaPlayer(stream);
        player.play(); // Playing live stream: Live Concert

        // Only Song can be downloaded
        song.download(); // Downloading song: Blinding Lights

        // podcast.download(); // ❌ Compile error — Podcast is not Downloadable
        // stream.download();  // ❌ Compile error — LiveStream is not Downloadable
    }
}