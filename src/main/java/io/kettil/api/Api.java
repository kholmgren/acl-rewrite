package io.kettil.api;

import io.kettil.tuple.Tuple;
import io.kettil.tuple.TupleUserset;

import java.util.List;

/**
 * From the Zanzibar white paper:
 * <p>
 * In addition to supporting ACL checks, Zanzibar also provides
 * APIs for clients to read and write relation tuples, watch
 * tuple updates, and inspect the effective ACLs.
 * <p>
 * A concept used throughout these API methods is that of
 * a `zookie`. A zookie is an opaque byte sequence encoding a
 * globally meaningful timestamp that reflects an ACL write, a
 * client content version, or a read snapshot. Zookies in ACL
 * read and check requests specify staleness bounds for snapshot
 * reads, thus providing one of Zanzibar’s core consistency
 * properties. We choose to use an opaque cookie instead of the
 * actual timestamp to discourage our clients from choosing
 * arbitrary timestamps and to allow future extensions.
 */
public class Api {
    /**
     * From the Zanzibar white paper:
     * <p>
     * Our clients read relation tuples to display ACLs or group
     * membership to users, or to prepare for a subsequent write.
     * A read request specifies one or multiple `tuplesets` and an
     * optional zookie.
     * <p>
     * Each `tupleset` specifies keys of a set of relation tuples. The
     * set can include a single tuple key, or all tuples with a given
     * object ID or userset in a namespace, optionally constrained
     * by a relation name. With the tuplesets, clients can look up
     * a specific membership entry, read all entries in an ACL or
     * group, or look up all groups with a given user as a direct
     * member. All tuplesets in a read request are processed at a
     * single snapshot.
     * <p>
     * With the zookie, clients can request a read snapshot no
     * earlier than a previous write if the zookie from the write
     * response is given in the read request, or at the same snapshot as
     * a previous read if the zookie from the earlier read response
     * is given in the subsequent request. If the request doesn’t
     * contain a zookie, Zanzibar will choose a reasonably recent
     * snapshot, possibly offering a lower-latency response than if
     * a zookie were provided.
     * <p>
     * Read results only depend on contents of relation tuples and
     * do not reflect userset rewrite rules. For example, even if the
     * `viewer` userset always includes the `owner` userset, reading
     * tuples with the `viewer` relation will not return tuples with
     * the owner relation. Clients that need to understand the
     * effective userset can use the Expand API (§2.4.5).
     *
     * @param tuple
     */
    public void read(Tuple tuple /* long zookie */) {
        //TODO: zookie
        //TODO: do not rewrite usersets
    }

    /**
     * From the Zanzibar white paper:
     * <p>
     * Clients may modify a single relation tuple to add or remove
     * an ACL. They may also modify all tuples related to an object
     * via a read-modify-write process with optimistic concurrency
     * control [21] that uses a read RPC followed by a write RPC:
     * <p>
     * 1. Read all relation tuples of an object, including a per-
     * object “lock” tuple.
     * <p>
     * 2. Generate the tuples to write or delete. Send the writes,
     * along with a touch on the lock tuple, to Zanzibar, with
     * the condition that the writes will be committed only if
     * the lock tuple has not been modified since the read.
     * <p>
     * 3. If the write condition is not met, go back to step 1.
     * <p>
     * The lock tuple is just a regular relation tuple used by
     * clients to detect write races.
     *
     * @param tuple
     */
    public void write(Tuple tuple) {
        //TODO: optimistic concurrency

        if (Tuple.isWild(tuple))
            throw new IllegalArgumentException("Wildcard tuple is not allowed");

    }

    /**
     * From the Zanzibar white paper:
     * <p>
     * Some clients maintain secondary indices of relation tuples
     * in Zanzibar. They can do so with our Watch API. A watch
     * request specifies one or more namespaces and a zookie
     * representing the time to start watching. A watch response
     * contains all tuple modification events in ascending timestamp
     * order, from the requested start timestamp to a timestamp
     * encoded in a heartbeat zookie included in the watch response.
     * The client can use the heartbeat zookie to resume watching
     * where the previous watch response left off.
     *
     * @param namespaces
     */
    public void watch(List<String> namespaces /* long zookie */) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * From the Zanzibar white paper:
     * <p>
     * A check request specifies a userset, represented by
     * `object#relation`, a putative user, often represented by an
     * authentication token, and a zookie corresponding to the desired
     * object version. Like reads, a check is always evaluated at a
     * consistent snapshot no earlier than the given zookie.
     * <p>
     * To authorize application content modifications, our clients
     * send a special type of check request, a `content-change` check.
     * A content-change check request does not carry a zookie and
     * is evaluated at the latest snapshot. If a content change is
     * authorized, the check response includes a zookie for clients
     * to store along with object contents and use for subsequent
     * checks of the content version. The zookie encodes the evaluation
     * snapshot and captures any possible causality from ACL
     * changes to content changes, because the zookie’s timestamp
     * will be greater than that of the ACL updates that protect the
     * new content (§2.2).
     *
     * @param userSet
     * @param userId
     */
    public void check(TupleUserset userSet, String userId /* boolean forModify, long zookie */) {
        //TODO: zookie
        //TODO: forModify -- check is different between mutating and reading

        if (userSet.isWild())
            throw new IllegalArgumentException("Wildcard userset is not allowed");

        //TODO: rewrite usersets

    }


    /**
     * From the Zanzibar white paper:
     * <p>
     * The Expand API returns the effective userset given an
     * `object#relation` pair and an optional zookie. Unlike the
     * Read API, Expand follows indirect references expressed
     * through userset rewrite rules. The result is represented by
     * a userset tree whose leaf nodes are user IDs or usersets
     * pointing to other `object#relation` pairs, and intermediate
     * nodes represent union, intersection, or exclusion operators.
     * Expand is crucial for our clients to reason about the complete
     * set of users and groups that have access to their objects,
     * which allows them to build efficient search indices for
     * access-controlled content.
     *
     * @param userSet
     */
    public void expand(TupleUserset userSet /* long zookie */) {
        //TODO: zookie
    }
}
