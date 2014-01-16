/**
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute
 * this software, either in source code form or as a compiled binary, for any
 * purpose, commercial or non-commercial, and by any means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the
 * public domain. We make this dedication for the benefit of the public at large
 * and to the detriment of our heirs and successors. We intend this dedication
 * to be an overt act of relinquishment in perpetuity of all present and future
 * rights to this software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to [http://unlicense.org]
 */
package ch.bfh.bti7301.w2013.battleship.game.players.ai;

import java.util.Arrays;
import java.util.Random;

import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;

/**
 * @author simon
 * 
 */
public class StrategyStack {

	private Coordinates[] s = null;

	private int pointer = 0;

	public StrategyStack() {
		this(16);
	}

	public StrategyStack(int initialSize) {
		s = (Coordinates[]) new Coordinates[initialSize];
	}

	public void push(Coordinates o) {

		if (pointer == s.length) {
			s = Arrays.copyOf(s, s.length * 2);
		}
		s[pointer++] = o;
	}

	public Coordinates pop() {
		while (this.isEmpty()) {
			addRandom(5);
		}
		return s[(pointer--)-1];
	}

	public Coordinates top() {
		if (this.isEmpty()) {
			throw new StackOverflowError("Stack is empty");
		}
		return s[pointer];
	}

	public int size() {
		return s.length;
	}

	public boolean isEmpty() {
		return (s.length == 0);
	}

	public void addRandom(int number) {
		int sizeOfBoard = Game.getInstance().getRule().getBoardSize();

		Random rand = new Random();
		for (int i = 0; i <= number; i++) {
			this.push(new Coordinates(rand.nextInt(sizeOfBoard), rand
					.nextInt(sizeOfBoard)));
		}
	}

}
