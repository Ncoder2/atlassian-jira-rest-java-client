/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlassian.jira.rest.client.domain.input;

import com.atlassian.jira.rest.client.domain.BasicUser;
import com.atlassian.jira.rest.client.domain.Visibility;
import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.net.URI;

/**
 * Represents worklog item in JIRA. Is used to create new worklog or update existing one.
 * Contains also estimate adjustment options which are used only to adjust change of remaining
 * estimate (adjustEstimate and adjustEstimateValue).<br/>
 * Possible values for adjustEstimate and adjustEstimateValue are:<br/>
 * <li>When adjustEstimate is set to {@link AdjustEstimate#AUTO} or {@link AdjustEstimate#LEAVE} adjustEstimateValue
 * is not used</li>
 * <li>When adjustEstimate is set to {@link AdjustEstimate#NEW} then remaining estimate is set to adjustEstimateValue</li>
 * <li>When adjustEstimate is set to {@link AdjustEstimate#MANUAL} then remaining estimate is reduced by adjustEstimateValue</li>
 */
public class WorklogInput {
	@Nullable
	private final URI self;
	private final URI issueUri;
	@Nullable
	private final BasicUser author;
	@Nullable
	private final BasicUser updateAuthor;
	@Nullable
	private final String comment;
	private final DateTime startDate;
    @Nullable
    private final Integer minutesSpent;
    @Nullable
    private final String timeSpent;
    @Nullable
	private final Visibility visibility;

	@Nullable
	private final String adjustEstimateValue;
	private final AdjustEstimate adjustEstimate;

	/**
	 * Creates new WorklogInput with given values
	 * @param self URI to this worklog, pass null if this is new worklog item.
	 * @param issueUri URI to destination issue
	 * @param author author of this worklog
	 * @param updateAuthor author of worklog actualization
	 * @param comment comment attached to worklog
	 * @param startDate date of work start
	 * @param minutesSpent time spend in minutes. Use either this or timeSpent, but not both
     * @param timeSpent time spent. E.g. "1d 2h". Use either this or minutesSpent, but not both
	 * @param visibility visibility settings for this worklog
	 * @param adjustEstimate adjust estimate option
	 * @param adjustEstimateValue value for estimate adjustment. Only used when adjustEstimate is set
	 *                               to {@link AdjustEstimate#NEW} or {@link AdjustEstimate#MANUAL}
	 */
	public WorklogInput(@Nullable URI self, URI issueUri, @Nullable BasicUser author, @Nullable BasicUser updateAuthor,
			@Nullable String comment, DateTime startDate, @Nullable Integer minutesSpent, @Nullable String timeSpent, @Nullable Visibility visibility,
			AdjustEstimate adjustEstimate, @Nullable String adjustEstimateValue) {
		this.visibility = visibility;
		this.minutesSpent = minutesSpent;
        this.timeSpent = timeSpent;
		this.startDate = startDate;
		this.comment = comment;
		this.updateAuthor = updateAuthor;
		this.author = author;
		this.issueUri = issueUri;
		this.self = self;
		this.adjustEstimate = adjustEstimate;
		this.adjustEstimateValue = adjustEstimateValue;
	}

	/**
	 * Creates new WorklogInput with given values. Sets adjust estimate option to default value - {@link AdjustEstimate#AUTO}.
	 * @param self URI to this worklog, pass null if this is new worklog item.
	 * @param issueUri URI to destination issue
	 * @param author author of this worklog
	 * @param updateAuthor author of worklog actualization
	 * @param comment comment attached to worklog
	 * @param startDate date of work start
	 * @param minutesSpent time spend in minutes
	 * @param visibility visibility settings for this worklog
	 */
	public WorklogInput(@Nullable URI self, URI issueUri, @Nullable BasicUser author, @Nullable BasicUser updateAuthor,
			@Nullable String comment, DateTime startDate, int minutesSpent, @Nullable Visibility visibility) {
		this(self, issueUri, author, updateAuthor, comment, startDate, minutesSpent, null, visibility, AdjustEstimate.AUTO, null);
	}

    public WorklogInput(@Nullable URI self, URI issueUri, @Nullable BasicUser author, @Nullable BasicUser updateAuthor,
                        @Nullable String comment, DateTime startDate, String timeSpent, @Nullable Visibility visibility) {
        this(self, issueUri, author, updateAuthor, comment, startDate, null, timeSpent, visibility, AdjustEstimate.AUTO, null);
    }

    public static WorklogInput create(URI issueUri, @Nullable String comment, DateTime startDate, int minutesSpent) {
		return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setMinutesSpent(minutesSpent).build();
	}

	public static WorklogInput create(URI issueUri, @Nullable String comment, DateTime startDate, int minutesSpent, @Nullable Visibility visibility) {
		return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setMinutesSpent(minutesSpent)
				.setVisibility(visibility).build();
	}

    public static WorklogInput create(URI issueUri, @Nullable String comment, DateTime startDate, String timeSpent) {
        return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setTimeSpent(timeSpent).build();
    }

    public static WorklogInput create(URI issueUri, @Nullable String comment, DateTime startDate, String timeSpent, @Nullable Visibility visibility) {
        return new WorklogInputBuilder(issueUri).setComment(comment).setStartDate(startDate).setTimeSpent(timeSpent)
                .setVisibility(visibility).build();
    }

    @Nullable
	public URI getSelf() {
		return self;
	}

	public URI getIssueUri() {
		return issueUri;
	}

	@Nullable
	public BasicUser getAuthor() {
		return author;
	}

	@Nullable
	public BasicUser getUpdateAuthor() {
		return updateAuthor;
	}

	@Nullable
	public String getComment() {
		return comment;
	}

	public DateTime getStartDate() {
		return startDate;
	}

    @Nullable
	public Integer getMinutesSpent() {
		return minutesSpent;
	}

    @Nullable
    public String getTimeSpent() {
        return timeSpent;
    }

    @Nullable
	public Visibility getVisibility() {
		return visibility;
	}

	public AdjustEstimate getAdjustEstimate() {
		return adjustEstimate;
	}

	@Nullable
	public String getAdjustEstimateValue() {
		return adjustEstimateValue;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("self", self)
				.add("issueUri", issueUri)
				.add("author", author)
				.add("updateAuthor", updateAuthor)
				.add("comment", comment)
				.add("startDate", startDate)
				.add("minutesSpent", minutesSpent)
                .add("timeSpent", timeSpent)
				.add("visibility", visibility)
				.add("adjustEstimate", adjustEstimate)
				.add("adjustEstimateValue", adjustEstimateValue)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WorklogInput) {
			final WorklogInput that = (WorklogInput) obj;

			return Objects.equal(this.self, that.self)
					&& Objects.equal(this.issueUri, that.issueUri)
					&& Objects.equal(this.author, that.author)
					&& Objects.equal(this.updateAuthor, that.updateAuthor)
					&& Objects.equal(this.comment, that.comment)
					&& Objects.equal(this.startDate, that.startDate)
					&& Objects.equal(this.minutesSpent, that.minutesSpent)
                    && Objects.equal(this.timeSpent, that.timeSpent)
					&& Objects.equal(this.visibility, that.visibility)
					&& Objects.equal(this.adjustEstimate, that.adjustEstimate)
					&& Objects.equal(this.adjustEstimateValue, that.adjustEstimateValue);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(self, issueUri, author, updateAuthor, comment, startDate, minutesSpent, timeSpent,
                visibility, adjustEstimate, adjustEstimateValue);
	}

	public static enum AdjustEstimate {
		/**
		 * Set remaining estimate to given value.
		 */
		NEW,
		/**
		 * Leave estimate as is.
		 */
		LEAVE,
		/**
		 * Decrease estimate manually by given value.
		 */
		MANUAL,
		/**
		 * Automatically decrease estimate based on given time spent. This is the default value.
		 */
		AUTO;

		public final String restValue;

		private AdjustEstimate() {
			restValue = this.name().toLowerCase();
		}
	}
}

