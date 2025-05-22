package http.handler.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DurationAdapter extends TypeAdapter<Long> {

    @Override
    public void write(JsonWriter out, Long duration) throws IOException {
        //if (duration == null || duration == 0) {
        //out.nullValue();
        //}
        if (duration == null) {
            out.nullValue();
        } else {
            out.value(duration);
        }
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0L;
        }
        return in.nextLong();
    }
}

