package main;

import java.awt.EventQueue;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JSONProcessor 
{
	public JSONProcessor()
	{
		InputStream is = this.getClass().getResourceAsStream("/sounds/data.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		try
		{
			JsonReader jr = new JsonReader(br);
			handle(jr);
			
			is.close();
			br.close();
		}
		
		catch (Exception e) { e.printStackTrace(); }
	}
	
	private static void handle(JsonReader reader) throws IOException 
	{
		while (reader.hasNext())
		{
			JsonToken token = reader.peek();
			
			if (token.equals(JsonToken.BEGIN_OBJECT)) reader.beginObject();
			else if (token.equals(JsonToken.BEGIN_ARRAY)) processArray(reader);
			else if (token.equals(JsonToken.NAME)) System.out.println(reader.nextName());
			else if (token.equals(JsonToken.BOOLEAN)) System.out.println(reader.nextBoolean());
			else if (token.equals(JsonToken.STRING)) System.out.println(reader.nextString());
		}
	}   
	   
	private static void processArray(JsonReader reader) throws IOException 
	{
		reader.beginArray();
      
		while (true) 
		{
			JsonToken token = reader.peek(); 
  
			if (token.equals(JsonToken.END_ARRAY))
			{
	            reader.endArray();
	            System.out.println();
	            break; 
			} 
			else if (token.equals(JsonToken.BEGIN_OBJECT)) handle(reader); 
			else if (token.equals(JsonToken.END_OBJECT)) reader.endObject();
			else System.out.print(reader.nextString() + " ");
      }
   } 
	
	public static void main(String[] args) { EventQueue.invokeLater(JSONProcessor::new); }
}
