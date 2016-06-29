package zhy2002.aspectjexamples.test;


import org.junit.Before;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class EscapeGcObject {
    public static EscapeGcObject lastEscaped;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        lastEscaped = this;
    }
}
public class GcTest {

    @Test
    public void weakReferenceIsAddedToQueueWhenObjectIsReadyToBeFinalized() throws Exception {

        EscapeGcObject escapeGcObject = new EscapeGcObject();
        ReferenceQueue<EscapeGcObject> referenceQueue = new ReferenceQueue<>();
        WeakReference<EscapeGcObject> reference = new WeakReference<>(escapeGcObject, referenceQueue);
        escapeGcObject = null;
        System.gc();
        Thread.sleep(100); //wait for gc thread to be called

        assertThat(EscapeGcObject.lastEscaped, notNullValue());
        assertThat(referenceQueue.poll(), sameInstance(reference));
        assertThat(reference.get(), nullValue());
    }

    @Test
    public void phantomReferenceIsAddedToQueueWhenObjectIsClaimed() throws Exception{


        EscapeGcObject escapeGcObject = new EscapeGcObject();
        ReferenceQueue<EscapeGcObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<EscapeGcObject> reference = new PhantomReference<>(escapeGcObject, referenceQueue);
        escapeGcObject = null;
        System.gc();
        Thread.sleep(100); //wait for gc thread to be called

        assertThat(EscapeGcObject.lastEscaped, notNullValue());
        assertThat(referenceQueue.poll(), nullValue());
        assertThat(reference.get(), nullValue());

        EscapeGcObject.lastEscaped = null;
        System.gc();
        Thread.sleep(100);

        assertThat(EscapeGcObject.lastEscaped, nullValue());
        assertThat(referenceQueue.poll(), sameInstance(reference));
        assertThat(reference.get(), nullValue());

    }
}
