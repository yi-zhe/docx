package com.ibeifeng.hadoop.senior.mapreduce;

import java.io.IOException;

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

public class ModuleMapReduce extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		ModuleMapReduce test = new ModuleMapReduce();
		ToolRunner.run(conf, test, args);
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = Job.getInstance(conf, getClass().getSimpleName());
		job.setJarByClass(getClass());

		// input
		Path path = new Path(args[0]);
		FileInputFormat.addInputPath(job, path);

		job.setMapperClass(ModuleMapper.class);
		// TODO
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setReducerClass(ModuleReducer.class);
		// TODO
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// output
		Path out = new Path(args[1]);
		FileOutputFormat.setOutputPath(job, out);

		// submit job
		boolean isSuccess = job.waitForCompletion(true);

		return isSuccess ? 0 : 1;
	}

	// TODO
	public static class ModuleMapper extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			// TODO
		}

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {

			// TODO
		}

		@Override
		protected void cleanup(Context context) throws IOException,
				InterruptedException {
			// TODO
		}
	}

	// TODO
	public static class ModuleReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			// TODO
		}

		@Override
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {

			// TODO
		}

		@Override
		protected void cleanup(Context context) throws IOException,
				InterruptedException {
			// TODO
		}
	}
}
