package com.ibeifeng.hadoop.senior.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapReduceTask extends Configured implements Tool {

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		MapReduceTask test = new MapReduceTask();

		ToolRunner.run(conf, test, args);
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = Job.getInstance(conf, getClass().getSimpleName());
		job.setJarByClass(getClass());

		// input
		Path path = new Path(args[0]);
		FileInputFormat.addInputPath(job, path);

		// map
		job.setMapperClass(MapperClass.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// reduce
		job.setReducerClass(ReducerClass.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// output
		Path out = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, out);

		// submit job
		boolean isSuccess = job.waitForCompletion(true);

		return isSuccess ? 0 : 1;
	}

	public static class MapperClass extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		private Text mapOutputKey = new Text();
		private static final IntWritable mapOutputValue = new IntWritable(1);

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String word = tokenizer.nextToken();
				mapOutputKey.set(word);
				context.write(mapOutputKey, mapOutputValue);
			}
		}

	}

	public static class ReducerClass extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private static final IntWritable outputValue = new IntWritable();

		@Override
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {

			int sum = 0;

			for (IntWritable value : values) {
				sum += value.get();
			}

			outputValue.set(sum);
			context.write(key, outputValue);
		}
	}
}
