require 'erb'
require 'ostruct'
require 'java'

# Renders an ERB template against a hashmap of variables.
# Template should be a Java InputStream
def render(template, variables)
  context = OpenStruct.new(variables).instance_eval do
    variables.each do |k, v|
      instance_variable_set(k, v) if k[0] == '@'
    end

    def partial(partial_name, options={})
      new_variables = marshal_dump.merge(options[:locals] || {})
      Java::com::itl::erb::ERB.render(partial_name, new_variables)
    end
    binding
  end
  ERB.new(template.to_io.read).result(context);
end

# Renders an ERB template wrapped in an ERB layout.
# template and layout should both be Java InputStreams
def render_with_layout(template, layout, variables)
  render(layout, variables) do
    render(template, variables)
  end
end 