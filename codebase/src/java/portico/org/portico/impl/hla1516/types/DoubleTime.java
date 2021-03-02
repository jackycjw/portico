/*
 *   Copyright 2006 The Portico Project
 *
 *   This file is part of portico.
 *
 *   portico is free software; you can redistribute it and/or modify
 *   it under the terms of the Common Developer and Distribution License (CDDL) 
 *   as published by Sun Microsystems. For more information see the LICENSE file.
 *   
 *   Use of this software is strictly AT YOUR OWN RISK!!!
 *   If something bad happens you do not have permission to come crying to me.
 *   (that goes for your lawyer as well)
 *
 */
package org.portico.impl.hla1516.types;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import hla.rti1516.IllegalTimeArithmetic;
import hla.rti1516.InvalidLogicalTime;
import hla.rti1516.LogicalTime;
import hla.rti1516.LogicalTimeInterval;

public class DoubleTime implements LogicalTime
{
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------
	private static final long serialVersionUID = 98121116105109L;
	
	//----------------------------------------------------------
	//                   INSTANCE VARIABLES
	//----------------------------------------------------------
	private double time;
	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------

	public DoubleTime()
	{
		
	}
	
	public DoubleTime( double value )
	{
		this.time = value;
	}
	
	//----------------------------------------------------------
	//                    INSTANCE METHODS
	//----------------------------------------------------------
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////// Non-Standard Compliant Methods ////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public double getTime()
	{
		return this.time;
	}
	
	public void setTime( double time )
	{
		this.time = time;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////// Logical Time Interface Methods ////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public boolean isInitial()
	{
		return this.time == 0.0;
	}

	public boolean isFinal()
	{
		return this.time == Double.MAX_VALUE;
	}

	/**
     * Returns a LogicalTime whose value is (this + val).
     */
	public LogicalTime add( LogicalTimeInterval val ) throws IllegalTimeArithmetic
	{
		if( val instanceof DoubleTimeInterval )
		{
			return new DoubleTime( time + ((DoubleTimeInterval)val).getTime() );
		}
		else
		{
			throw new IllegalArgumentException( "Invalid argument: " + val.getClass() );
		}
	}

	/**
     * Returns a LogicalTime whose value is (this - val).
     */
	public LogicalTime subtract( LogicalTimeInterval val ) throws IllegalTimeArithmetic
	{
		if( val instanceof DoubleTimeInterval )
		{
			return new DoubleTime( time - ((DoubleTimeInterval)val).getTime() );
		}
		else
		{
			throw new IllegalArgumentException( "Invalid argument: " + val.getClass() );
		}
	}

	/**
     * Returns a LogicalTimeInterval whose value is the time interval between this and val.
     */
	public LogicalTimeInterval distance( LogicalTime val )
	{
		if( val instanceof DoubleTimeInterval )
		{
			double result = Math.abs( time - ((DoubleTimeInterval)val).getTime() );
			return new DoubleTimeInterval( result );
		}
		else
		{
			throw new IllegalArgumentException( "Invalid argument: " + val.getClass() );
		}
	}

	public int compareTo( Object other )
	{
		if( other instanceof DoubleTime )
		{
			double otherValue = ((DoubleTime)other).time;
			if( this.time > otherValue )
			{
				return 1;
			}
			else if( this.time == otherValue )
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			throw new IllegalArgumentException( "Invalid argument: " + other.getClass() );
		}
	}

	/**
     * Returns true iff this and other represent the same logical time Supports standard Java
     * mechanisms.
     */
	public boolean equals( Object other )
	{
		if( other instanceof DoubleTime )
		{
			if( ((DoubleTime)other).time == this.time )
			{
				return true;
			}
		}
		
		return false;
	}

	/**
     * Two LogicalTimes for which equals() is true should yield same hash code
     */
	public int hashCode()
	{
		return ( Double.valueOf(time) ).hashCode();
	}

	public String toString()
	{
		return "" + time;
	}

	public int encodedLength()
	{
		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream( byteStream );
			stream.writeDouble( this.time );
			stream.close();
			return byteStream.toByteArray().length;
		}
		catch( Exception e )
		{
			// shouldn't happen
			return -1;
		}
	}

	public void encode( byte[] buffer, int offset )
	{
		try
		{
			// convert the into an array
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			DataOutputStream stream = new DataOutputStream( byteStream );
			stream.writeDouble( this.time );
			stream.close();
			byte[] bytes = byteStream.toByteArray();
			
			// copy it into the given array
			System.arraycopy( bytes, 0, buffer, offset, bytes.length );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}
	}
	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------
	public static double fromTime( LogicalTime logical ) throws InvalidLogicalTime
	{
		try
		{
			return ((DoubleTime)logical).time;
		}
		catch( ClassCastException cce )
		{
			throw new InvalidLogicalTime( "Expecting DoubleTime, found: " + logical.getClass() );
		}
		catch( NullPointerException npe )
		{
			throw new InvalidLogicalTime( "Expecting DoubleTime, found: null" );
		}
	}
}
