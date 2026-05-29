package com.simeon.picoinspector.time;

    public class CaptureIdParser {
        public String extractCaptureId(String url) {
            // Example URL: https://picolog.app/api/v1/public/capture/data/block/2f80711e-7b2c-4306-b6bf-dc6b15dcfffc/tc-08.DEMO%7C123.ch2/1/1779116000000
            String[] parts = url.split("/");
            String captureId = parts[parts.length - 4];
            return captureId;
        }
    }
