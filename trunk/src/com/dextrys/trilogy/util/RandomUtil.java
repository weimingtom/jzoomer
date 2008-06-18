/*
 * @author talent_marquis<��˺��>
 * Email: talent_marquis@163.com
 * Copyright (C) 2007 talent_marquis<��˺��>
 * All rights reserved.
 */
package com.dextrys.trilogy.util;

import java.util.Arrays;

import org.eclipse.swt.graphics.RGB;

public class RandomUtil
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		//System.out.println( getRandomNormalString( 8 ) );
		int[] test = getRandomIntWithoutReduplicate( 0, 40, 39 );
		Arrays.sort( test );
		for( int i : test )
		{
			System.out.println( i );
		}
	}

	/**
	 * get a integer array filled with random integer without reduplicate [min, max)
	 * @param min	the minimum value
	 * @param max the maximum value
	 * @param size the capacity of the array
	 * @return a integer array filled with random integer without redupulicate
	 */
	public static int[] getRandomIntWithoutReduplicate( int min, int max, int size )
	{
		int[] result = new int[size];
		
		int arraySize = max - min;
		int[] intArray = new int[arraySize];
		// init intArray
		for( int i = 0 ; i < intArray.length ; i++ )
		{
			intArray[i] = i + min;
		}
		// get randome interger without reduplicate
		for( int i = 0 ; i < size ; i++ )
		{
			int c = getRandomInt( min, max - i );
			int index = c - min;
			swap( intArray, index, arraySize - 1 - i );
			result[i] = intArray[ arraySize - 1 - i ];
		}
				
		return result;
	}
	
	private static void swap( int[] array, int x, int y )
	{
		int temp = array[x];
		array[x] = array[y];
		array[y] = temp;
	}
	
	/**
	 * get a random Integer with the range [min, max)
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the random Integer value
	 */
	public static int getRandomInt( int min, int max )
	{
		// include min, exclude max
		int result = min + new Double( Math.random() * ( max - min ) ).intValue();

		return result;
	}

	/**
	 * get a random double with the range [min, max)
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the random double value
	 */
	public static double getRandomDouble( double min, double max )
	{
		// include min, exclude max
		double result = min + ( Math.random() * ( max - min ) );
		return result;
	}

	/**
	 * 
	 * @return a random char with the range ASCII 33(!) to ASCII 126(~)
	 */
	public static char getRandomChar()
	{
		// from ASCII code 33 to ASCII code 126
		int firstChar = 33; // "!"
		int lastChar = 126; // "~"
		char result = ( char ) ( getRandomInt( firstChar, lastChar + 1 ) );
		return result;
	}
	
	/**
	 * 
	 * @return a random rgb color
	 */
	public static RGB getRandomRGB()
	{
		int red = getRandomInt(0,256);
		int green = getRandomInt(0,256);
		int blue = getRandomInt(0,256);
		
		return new RGB( red, green, blue );
	}
	
	/**
	 * 
	 * @return a random char with the range [0-9],[a-z],[A-Z]
	 */
	public static char getRandomNormalChar()
	{
		// include 0-9,a-z,A-Z
		int number = getRandomInt( 0, 62 );
		int zeroChar = 48;
		int nineChar = 57;
		int aChar = 97;
		int zChar = 122;
		int AChar = 65;
		int ZChar = 90;
		
		char result;
		
		if( number < 10 )
		{
			result =  ( char ) ( getRandomInt( zeroChar, nineChar + 1 ) );
			return result;

		}
		else if( number >= 10 && number < 36 )
		{
			result = ( char ) ( getRandomInt( AChar, ZChar + 1 ) );
			return result;
		}
		else if( number >= 36 && number < 62 )
		{
			result =  ( char ) ( getRandomInt( aChar, zChar + 1 ) );
			return result;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 
	 * @param length the length of the String
	 * @return a String filled with random char
	 */
	public static String getRandomString( int length )
	{
		// include ASCII code from 33 to 126
		StringBuffer result = new StringBuffer();
		for( int i = 0; i < length; i++ )
		{
			result.append( getRandomChar() );
		}
		return result.toString();
	}
	
	/**
	 * 	
	 * @param length the length of the String
	 * @return a String filled with normal random char
	 */
	public static String getRandomNormalString( int length )
	{
		// include 0-9,a-z,A-Z
		StringBuffer result = new StringBuffer();
		for( int i = 0; i < length; i++)
		{
			result.append( getRandomNormalChar() );
		}
		return result.toString();
	}
}
