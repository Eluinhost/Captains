package com.publicuhc.captains;

public class DraftMode
{
    private boolean isInDraftMode = false;

    /**
     * @return whether or not we are in draft mode or not
     */
    public boolean isInDraftMode()
    {
        return isInDraftMode;
    }

    /**
     * Start the draft mode
     *
     * @throws java.lang.IllegalStateException if already in draft mode
     */
    public void startDraftMode()
    {
        if(isInDraftMode()) {
            throw new IllegalStateException();
        }
        isInDraftMode = true;

        //TODO anything needed to start draft mode
    }

    /**
     * Ends draft mode
     */
    public void endDraftMode()
    {
        //TODO anything we need to force stop draft mode
        isInDraftMode = false;
    }
}
