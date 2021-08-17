package com.jack.applications.database;

import com.jack.applications.database.daos.TmdbDAOimpl;
import com.jack.applications.database.models.*;
import com.jack.applications.utils.JsonMapper;
import com.jack.applications.webservice.handlers.RoomGarbageCollector;
import com.jack.applications.webservice.handlers.RoomHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Component
public class DatabaseHandler {

}
