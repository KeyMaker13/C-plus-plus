/**
 * Same as before, but perform alpha-beta pruning.
 * The main routine should make the call with
 * alpha = COMP_LOSS and beta = COMP_WIN.
 */
int TicTacToe::findCompMove( int & bestMove, int alpha, int beta )
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
        value = alpha; bestMove = 1;
        for( i = 1; i <= 9 && value < beta; i++ )  // Try each square
        {
            if( isEmpty( i ) )
            {
                place( i, COMP );
                responseValue = findHumanMove( dc, value, beta );
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
