import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Post {
    String content;
    long timestamp;
    Set<String> tags;
    int likes;

    public Post(String content, long timestamp, Set<String> tags) {
        this.content = content;
        this.timestamp = timestamp;
        this.tags = tags;
        this.likes = 0;
    }

    public void like() {
        likes++;
    }
}

class SocialMediaAnalytics {
    private Map<Integer, Post> posts;
    private Map<String, Integer> tagFrequency;
    private PriorityQueue<Map.Entry<String, Integer>> tagHeap;

    public SocialMediaAnalytics() {
        posts = new HashMap<>();
        tagFrequency = new HashMap<>();
        tagHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
    }

    public void addPost(int id, String content, long timestamp, Set<String> tags) {
        Post post = new Post(content, timestamp, tags);
        posts.put(id, post);
        for (String tag : tags) {
            tagFrequency.put(tag, tagFrequency.getOrDefault(tag, 0) + 1);
        }
        updateTagHeap();
        System.out.println("Post added with ID: " + id);
    }

    public void likePost(int id) {
        Post post = posts.get(id);
        if (post != null) {
            post.like();
            System.out.println("Post with ID: " + id + " liked.");
        } else {
            System.out.println("Post not found.");
        }
    }

    public void searchPosts(String keyword) {
        for (Map.Entry<Integer, Post> entry : posts.entrySet()) {
            if (entry.getValue().content.contains(keyword)) {
                System.out.println("Post ID: " + entry.getKey() + " - " + entry.getValue().content);
            }
        }
    }

    public void topTags() {
        System.out.println("Top Tags:");
        for (Map.Entry<String, Integer> entry : tagHeap) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void updateTagHeap() {
        tagHeap.clear();
        tagHeap.addAll(tagFrequency.entrySet());
    }

    public static void main(String[] args) {
        SocialMediaAnalytics analytics = new SocialMediaAnalytics();

        // Example usage
        Set<String> tags1 = new HashSet<>(Arrays.asList("Java", "Programming"));
        Set<String> tags2 = new HashSet<>(Arrays.asList("AI", "MachineLearning"));

        analytics.addPost(1, "Learning Java programming.", System.currentTimeMillis(), tags1);
        analytics.addPost(2, "Exploring AI and machine learning.", System.currentTimeMillis(), tags2);

        analytics.likePost(1);
        analytics.searchPosts("Java");
        analytics.topTags();
    }
}
