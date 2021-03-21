/*
 *   Copyright 2012 The Portico Project
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
package org.portico.impl.hla1516e.types.encoding;

import org.portico.utils.bithelpers.BitHelpers;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAoctetPairLE;

public class HLA1516eOctetPairLE extends HLA1516eDataElement implements HLAoctetPairLE
{
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------

	//----------------------------------------------------------
	//                   INSTANCE VARIABLES
	//----------------------------------------------------------
	private short value;

	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------
	public HLA1516eOctetPairLE()
	{
		this.value = Short.MIN_VALUE;
	}

	public HLA1516eOctetPairLE( short value )
	{
		this.value = value;
	}

	//----------------------------------------------------------
	//                    INSTANCE METHODS
	//----------------------------------------------------------
	/**
	 * Returns the short value of this element.
	 * 
	 * @return short value
	 */
	public short getValue()
	{
		return this.value;
	}

	/**
	 * Sets the short value of this element.
	 * 
	 * @param value New value.
	 */
	public void setValue( short value )
	{
		this.value = value;
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// DataElement Methods //////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public final int getOctetBoundary()
	{
		return 2;
	}

	@Override
	public final int getEncodedLength()
	{
		return 2;
	}

	@Override
	public final void encode( ByteWrapper byteWrapper ) throws EncoderException
	{
		byteWrapper.align( getOctetBoundary() );
		byte[] bytes = new byte[getEncodedLength()];
		BitHelpers.putShortLE( value, bytes, 0 );
		byteWrapper.put( bytes );
	}

	@Override
	public final void decode( ByteWrapper byteWrapper ) throws DecoderException
	{
		byteWrapper.align( getOctetBoundary() );
		byteWrapper.verify( getEncodedLength() );
		
		byte[] bytes = new byte[getEncodedLength()];
		byteWrapper.get( bytes );
		this.value = BitHelpers.readShortLE( bytes, 0 );
	}

	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------
}
