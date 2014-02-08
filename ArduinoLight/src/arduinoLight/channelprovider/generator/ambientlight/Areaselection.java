package arduinoLight.channelprovider.generator.ambientlight;


/**
 * Represents a relative part of a 2D Area (i.e. 'top left quarter' == (r0c0 = true)(r0c1 = false)(... = false)).
 * This class is thread-safe (Java-monitor-pattern).
 */
public class Areaselection
{
	private boolean[][] _matrix;
	
	
	/**
	 * Creates a new Areaselection with the specified Amount of Rows and Columns.
	 */
	public Areaselection(int columns, int rows)
	{
		if (columns < 1 || rows < 1)
		{
			throw new IllegalArgumentException();
		}
		
		_matrix = new boolean[columns][rows];
	}

	/**
	 * Sets a cell to the given 'flag'-value.
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public synchronized void setCell(int x, int y, boolean flag)
	{
		validateCoordinates(x, y);
		
		_matrix[x][y] = flag;
	}
	
	/**
	 * Returns the value of a cell.
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public synchronized boolean getCell(int x, int y)
	{
		validateCoordinates(x, y);
		
		return _matrix[x][y];
	}

	/**
	 * Creates a new matrix that contains the old matrix as good as possible.
	 * If the new one is bigger, the old one is copied into the new one.
	 * If the new one is smaller, the new one is a subsection of the old one.
	 * @param newColCount new Amount of Columns
	 * @param newRowCount new Amount of Rows
	 */
	public void changeSize(int newColCount, int newRowCount)
	{
		if (newColCount < 1 || newRowCount < 1)
		{
			throw new IllegalArgumentException();
		}
		boolean[][] newMatrix = new boolean[newColCount][newRowCount];
		int smallestColCount = Math.min(newColCount, getColumns());
		int smallestRowCount = Math.min(newRowCount, getRows());
		
		synchronized (_matrix)
		{
			for (int c = 0; c < smallestColCount; c++)
			{
				for (int r = 0; r < smallestRowCount; r++)
				{
					newMatrix[c][r] = _matrix[c][r];
				}
			}
			_matrix = newMatrix;
		}
	}

	/**
	 * Sets every cell of the matrix to false.
	 */
	public void clear()
	{
		synchronized (_matrix)
		{
			_matrix = new boolean[_matrix.length][_matrix[0].length];
		}
	}
	
	/**
	 * Returns the Amount of Columns.
	 */
	public synchronized int getColumns()
	{
		return _matrix[0].length;
	}
	
	/**
	 * Returns the Amount of Rows.
	 */
	public synchronized int getRows()
	{
		return _matrix.length;
	}
	
	/**
	 * Returns a boolean Array that represents the selections made.
	 */
	public boolean[][] toBooleanArray()
	{
		boolean[][] copy = new boolean[_matrix.length][_matrix[0].length];
		
		synchronized (_matrix)
		{
			for (int x = 0; x < _matrix.length; x++)
			{
				for (int y = 0; y < _matrix[0].length; y++)
				{
					copy[x][y] = _matrix[x][y];
				}
			}
		}
		
		return copy;
	}
	
	
	
	private void validateCoordinates(int x, int y)
	{
		if (x >= getColumns() || x < 0 || y < 0 || y >= getRows())
		{
			throw new IllegalArgumentException();
		}
	}
}