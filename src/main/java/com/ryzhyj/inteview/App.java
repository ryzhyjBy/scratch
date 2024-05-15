package com.ryzhyj.inteview;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ryzhyj.inteview.components.Board;
import com.ryzhyj.inteview.components.Scratch;
import com.ryzhyj.inteview.config.Config;
import com.ryzhyj.inteview.helpers.Params;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Params params = new Params();
        if (params.init(args)) {
            ObjectMapper objectMapper = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build();
            Config config = objectMapper.readValue(new File(params.getConfigPath()), Config.class);

            Board output = new Scratch(config).scratch(params.getBettingAmount());

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, output);
        }
    }
}
