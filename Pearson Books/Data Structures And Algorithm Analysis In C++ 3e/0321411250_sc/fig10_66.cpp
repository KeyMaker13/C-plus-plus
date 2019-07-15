/**
 * Recursive function to find best move for computer.
 * Returns the evaluation and sets bestMove, which
 * ranges from 1 to 9 and indicates the best square to occupy.
 * Possible evaluations satisfy COMP_LOSS < DRAW < COMP_WIN.
 * Complementary function findHumanMove is Figure 10.67.
 */
int TicTacToe::findCompMove( int & bestMove )
{
    int i, responseValue;
    int dc;   // dc means don't care; its value is unused
    int value;

    if( fullBoard( ) )
        value = DRAW;
    else
    if( immediateCompWin( bestMove ) )
        return COMP_WIN;    // bestMove will be set by immediateCompWin
    else
    {
        value = COMP_LOSS; bestMove = 1;
        for( i = 1; i <= 9; i++ )  // Try each square
        {
            if( isEmpty( i ) )
            {
                place( i, COMP );
                responseValue = findHumanMove( dc );
                unplace( i );  // Restore board

                if( responseValue > value )
                {
                      // Update best move
                    value = responseValue;
                    bestMove = i;
                }
            }
        }
    }
    return value;
}
