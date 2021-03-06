/*
 * Copyright (c) 2018 Patrick Beuks (killje) <patrick.beuks@gmail.com>
 * Copyright (c) 2018 CC007 <Coolcat_the_best2@hotmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.itemdatabase.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.HashSet;
import java.util.Set;

public class Trie
{
	private TrieNode root = new TrieNode();

	public void insert(String word, int itemId) {
		String lowerCaseWord = word.toLowerCase();
		TrieNode current = root;
		current.addResults(itemId);

		for (int i = 0; i < lowerCaseWord.length(); i++) {
			current = current.getChildren()
				.computeIfAbsent(lowerCaseWord.toLowerCase().charAt(i), c -> new TrieNode());

			current.addResults(itemId);
		}
	}

	public Set<Integer> find(String word) {
		String lowerCaseWord = word.toLowerCase();
		TrieNode current = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = lowerCaseWord.charAt(i);
			TrieNode node = current.getChildren().get(ch);
			if (node == null) {
				return new HashSet<>();
			}
			current = node;
		}
		return current.getResults();
	}

	@VisibleForTesting
	public TrieNode getRoot()
	{
		return root;
	}
}
