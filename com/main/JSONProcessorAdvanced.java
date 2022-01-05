package main;

import java.awt.EventQueue;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class JSONProcessorAdvanced 
{
	private Map<String, List<String>> dirmap;
	
	public JSONProcessorAdvanced()
	{
		this.dirmap = null;
		
		InputStream is = this.getClass().getResourceAsStream("/data/sample.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		JsonReader jr = new JsonReader(br);
		
		double start = System.nanoTime();
		
		try 
		{ 
			dirmap = readJSONFile(jr);
			is.close();
			br.close();
			jr.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		
		//prints all Keys and its associated values (if the key has some)
		//NOT Recommended to use with the 'bigdata.json' file!
		
		
		for (Map.Entry<String, List<String>> itor: this.dirmap.entrySet())
		{
			System.out.println(itor.getKey());
			
			if (!Objects.isNull(itor.getValue()))
			{
				Iterator<String> itr = itor.getValue().iterator();
				
				while (itr.hasNext())
				{
					System.out.println(itr.next());
				}
			}
			
			else System.out.printf("Error! There is no value associated with key %s\n", itor.getKey());
			
			System.out.println();
		}
		
		
		double end = System.nanoTime();
		System.out.printf("Done! The Operation took %.2f ms\n", (end - start) / 1000000);
	}
	   
	private Map<String, List<String>> readJSONFile(JsonReader reader) throws IOException
	{
		Map<String, List<String>> returner = new HashMap<>();
		
		String key = null;
		List<String> values = null;
		
		reader.beginArray();
		while (reader.hasNext())
		{
			reader.beginObject();
			
			while (reader.hasNext())
			{
				String name = reader.nextName();
				
				if (name.equals("id")) key = reader.nextString();
				else if (name.equals("various"))
				{
					if (reader.nextBoolean())
					{
						name = reader.nextName();
						if (name.equals("variants")) values = processArray(reader);
					}
					else values = null;
				}
				else reader.skipValue();
				
				returner.put(key, values);
			}
			reader.endObject();
		}
		
		return returner;
	}
	
	private List<String> processArray(JsonReader reader) throws IOException
	{
		List<String> returner = new ArrayList<>();
		reader.beginArray();
		while (reader.hasNext()) returner.add(reader.nextString());
		reader.endArray();
		
		return returner;
	}
	
	public static void main(String[] args) { EventQueue.invokeLater(JSONProcessorAdvanced::new); }
}
