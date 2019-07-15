int TicTacToe::findHumanMove( int & bestMove )
{
    int i, responseValue;
    int dc;   // dc means don't care; its value is unused
    int value;

    if( fullBoard( ) )
        value = DRAW;
    else
    if( immediateHumanWin( bestMove ) )
        return COMP_LOSS;
    else
    {
        value = COMP_WIN; bestMove = 1;
        for( i = 1; i <= 9; i++ )  // Try each square
        {
            if( isEmpty( i ) )
            {
                place( i, HUMAN );
                responseValue = findCompMove( dc );
                unplace( i );  // Restore board

                if( responseValue < value )
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
