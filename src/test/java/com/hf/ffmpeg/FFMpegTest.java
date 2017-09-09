package com.hf.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krt on 2017/8/26.
 */
public class FFMpegTest {
    
    public static void main(String[] args) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("E:\\safe\\ffmpeg-20170824-f0f4888-win64-static\\bin\\ffmpeg.exe");
        FFprobe ffprobe = new FFprobe("E:\\safe\\ffmpeg-20170824-f0f4888-win64-static\\bin\\ffprobe.exe");

        FFmpegBuilder builder = new FFmpegBuilder()

                .setInput("/temp/test.mp4")     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists

                .addOutput("/temp/output.mp4")   // Filename for the destination
                .setFormat("mp4")        // Format is inferred from filename, or can be set
                //.setTargetSize(250_000)  // Aim for a 250KB file

                .disableSubtitle()       // No subtiles

                .setAudioChannels(2)         // Mono audio
                .setAudioCodec("aac")        // using the aac codec
                .setAudioSampleRate(48_000)  // at 48KHz
                .setAudioBitRate(32768)      // at 32 kbit/s

                .setVideoCodec("libx264")     // Video using x264
                //.setVideoFrameRate(24, 1)     // at 24 frames per second
                //.setVideoResolution(640, 480) // at 640x480 resolution

                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        // Or run a two-pass encode (which is slower at the cost of better quality)
       // executor.createTwoPassJob(builder).run();
    }

    @Test
    public void test1() throws IOException, InterruptedException {
        List<String> commend = new ArrayList<String>();
        commend.add("E:\\safe\\mplayer-svn-37946-6-x86_64\\mencoder.exe");
        commend.add("D:\\temp\\test.mp4");
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add("D:\\temp\\test.mp4.avi");
        ProcessBuilder builder = new ProcessBuilder();
        Process process = builder.command(commend).redirectErrorStream(true).start();
        //new PrintStream(process.getInputStream());
        //new PrintStream(process.getErrorStream());
        process.waitFor();
    }
}
