/*
 * Copyright (c) 2008, Heng Yuan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Heng Yuan nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Heng Yuan ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Heng Yuan BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.yuanheng.cookcc.dfa;

import java.util.Map;

import org.yuanheng.cookcc.doc.LexerStateDoc;
import org.yuanheng.cookcc.lexer.Lexer;

/**
 * Utility class that computes the full table.
 *
 * @author Heng Yuan
 * @version $Id$
 */
public class FullTable
{
	private final Lexer m_lexer;

	public FullTable (Lexer lexer)
	{
		m_lexer = lexer;
	}

	public int[][] getTable ()
	{
		DFATable dfa = m_lexer.getDFA ();
		int rows = dfa.size ();
		int cols = m_lexer.getCCL ().MAX_SYMBOL + 1;
		int[][] table = new int[rows][cols];
		int[] groups = m_lexer.getECS ().getGroups ();
		for (int i = 0; i < rows; ++i)
		{
			char[] states = dfa.getRow (i).getStates ();
			int[] array = table[i];
			for (int j = 0; j < cols; ++j)
				array[j] = states[groups[j]];
		}
		return table;
	}

	public String[] getLexerStates ()
	{
		LexerStateDoc[] lexerStates = m_lexer.getLexerStates ();
		String[] states = new String[lexerStates.length];
		for (int i = 0; i < lexerStates.length; ++i)
			states[i] = lexerStates[i].getName ();
		return states;
	}

	public int[] getLexerBegins ()
	{
		return m_lexer.getBeginLocations ();
	}

	public void setup (Map<String, Object> ctx)
	{
		ctx.put ("dfa", getTable ());

		ctx.put ("lexerStates", getLexerStates ());
		ctx.put ("lexerBegins", getLexerBegins ());
	}
}
